package com.code.block.feature.profile.presentation.profilescreen

import android.graphics.BitmapFactory
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.block.R
import com.code.block.core.domain.model.Comment
import com.code.block.core.domain.model.Post
import com.code.block.core.domain.resource.Resource
import com.code.block.core.domain.state.PageState
import com.code.block.core.domain.type.ParentType
import com.code.block.core.presentation.components.Screen
import com.code.block.core.usecase.GetOwnUserIdUseCase
import com.code.block.core.util.ui.UiEvent
import com.code.block.core.util.ui.UiText
import com.code.block.core.util.ui.liker.Liker
import com.code.block.core.util.ui.paging.PaginatorImpl
import com.code.block.usecase.post.PostUseCases
import com.code.block.usecase.profile.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val postUseCases: PostUseCases,
    private val getOwnUserIdUseCase: GetOwnUserIdUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val liker: Liker,
) : ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _ownPagingState = mutableStateOf<PageState<Post>>(PageState())
    val ownPagingState: State<PageState<Post>> = _ownPagingState

    private val _likePagingState = mutableStateOf<PageState<Post>>(PageState())
    val likePagingState: State<PageState<Post>> = _likePagingState

    private val _commentPagingState = mutableStateOf<PageState<Comment>>(PageState())
    val commentPagingState: State<PageState<Comment>> = _commentPagingState

    private val commentPaginator = PaginatorImpl(
        onLoadUpdated = { isLoading ->
            _commentPagingState.value = commentPagingState.value.copy(
                isLoading = isLoading,
            )
        },
        onRequest = { page ->
            val userId = savedStateHandle.get<String>("userId") ?: getOwnUserIdUseCase()
            profileUseCases.commentsUseCase(
                userId = userId,
                page = page,
            )
        },
        onSuccess = { comments ->
            _commentPagingState.value = commentPagingState.value.copy(
                items = commentPagingState.value.items + comments,
                endReached = comments.isEmpty(),
                isLoading = false,
            )
        },
        onError = { uiText ->
            _eventFlow.emit(UiEvent.SnackBarEvent(uiText))
        },
    )

    private val ownPaginator = PaginatorImpl(
        onLoadUpdated = { isLoading ->
            _ownPagingState.value = ownPagingState.value.copy(
                isLoading = isLoading,
            )
        },
        onRequest = { page ->
            val userId = savedStateHandle.get<String>("userId") ?: getOwnUserIdUseCase()
            profileUseCases.getOwnPostsProfileUseCase(
                userId = userId,
                page = page,
            )
        },
        onSuccess = { posts ->
            _ownPagingState.value = ownPagingState.value.copy(
                items = ownPagingState.value.items + posts,
                endReached = posts.isEmpty(),
                isLoading = false,
            )
        },
        onError = { uiText ->
            _eventFlow.emit(UiEvent.SnackBarEvent(uiText))
        },
    )

    private val likePaginator = PaginatorImpl(
        onLoadUpdated = { isLoading ->
            _likePagingState.value = likePagingState.value.copy(
                isLoading = isLoading,
            )
        },
        onRequest = { page ->
            val userId = savedStateHandle.get<String>("userId") ?: getOwnUserIdUseCase()
            profileUseCases.getLikedPostsProfileUseCase(
                userId = userId,
                page = page,
            )
        },
        onSuccess = { posts ->
            _likePagingState.value = likePagingState.value.copy(
                items = likePagingState.value.items + posts,
                endReached = posts.isEmpty(),
                isLoading = false,
            )
        },
        onError = { uiText ->
            _eventFlow.emit(UiEvent.SnackBarEvent(uiText))
        },
    )

    init {
        loadOwnPosts()
        loadLikedPosts()
        loadComments()
    }

    fun loadOwnPosts() {
        viewModelScope.launch {
            ownPaginator.loadNextItems()
        }
    }

    fun loadLikedPosts() {
        viewModelScope.launch {
            likePaginator.loadNextItems()
        }
    }

    fun loadComments() {
        viewModelScope.launch {
            commentPaginator.loadNextItems()
        }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.GetProfile -> {
                getProfile(userId = event.userId)
            }
            is ProfileEvent.OwnPageLikePost -> {
                viewModelScope.launch {
                    ownLikeParent(
                        parentId = event.postId,
                    )
                }
            }
            is ProfileEvent.LikePageLikePost -> {
                viewModelScope.launch {
                    likeLikeParent(
                        parentId = event.postId,
                    )
                }
            }
            is ProfileEvent.LikeComment -> {
                viewModelScope.launch {
                    likeComment(event.commentId)
                }
            }
            is ProfileEvent.FollowMotion -> {
                followUser(event.userId)
            }
            ProfileEvent.DismissLogoutDialog -> {
                _state.value = _state.value.copy(
                    isLogoutDialogVisible = false,
                )
            }
            ProfileEvent.Logout -> {
                profileUseCases.logoutUseCase()
            }
            ProfileEvent.ShowLogoutDialog -> {
                _state.value = _state.value.copy(
                    isLogoutDialogVisible = true,
                )
            }
            ProfileEvent.ShowMenu -> {
                _state.value = _state.value.copy(
                    showMenu = !state.value.showMenu,
                )
            }
            is ProfileEvent.DeletePost -> {
                deletePost(event.post.id)
            }
            is ProfileEvent.Followers -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.Navigate(
                            Screen.FollowInfoScreen.route + "/followers" + "/${event.userId}",
                        ),
                    )
                }
            }
            is ProfileEvent.Followings -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.Navigate(
                            Screen.FollowInfoScreen.route + "/followings" + "/${event.userId}",
                        ),
                    )
                }
            }
        }
    }

    private fun deletePost(postId: String) {
        viewModelScope.launch {
            when (val result = postUseCases.deletePost(postId)) {
                is Resource.Success -> {
                    _ownPagingState.value = ownPagingState.value.copy(
                        items = ownPagingState.value.items.filter {
                            it.id != postId
                        },
                    )
                    _eventFlow.emit(
                        UiEvent.SnackBarEvent(
                            UiText.StringResource(
                                R.string.successfully_deleted_post,
                            ),
                        ),
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.SnackBarEvent(result.uiText ?: UiText.unknownError()),
                    )
                }
            }
        }
    }

    private fun likeComment(
        parentId: String,
    ) {
        viewModelScope.launch {
            val comments = commentPagingState.value.items
            val comment = comments.find { it.id == parentId }
            val currentlyLiked = comment?.isLiked == true
            val currentLikeCount = comment?.likeCount ?: 0
            val newComments = comments.map { comment1 ->
                if (comment1.id == parentId) {
                    comment1.copy(
                        isLiked = !comment1.isLiked,
                        likeCount = if (currentlyLiked) {
                            comment1.likeCount - 1
                        } else {
                            comment1.likeCount + 1
                        },
                    )
                } else {
                    comment1
                }
            }
            _commentPagingState.value = _commentPagingState.value.copy(
                items = newComments,
            )
            when (
                postUseCases.likeParentUseCase(
                    parentId = parentId,
                    parentType = ParentType.Comment.type,
                    isLiked = currentlyLiked,
                )
            ) {
                is Resource.Success -> Unit
                is Resource.Error -> {
                    val oldComments = comments.map { comment2 ->
                        if (comment2.id == parentId) {
                            comment2.copy(
                                isLiked = currentlyLiked,
                                likeCount = currentLikeCount,
                            )
                        } else {
                            comment2
                        }
                    }
                    _commentPagingState.value = _commentPagingState.value.copy(
                        items = oldComments,
                    )
                }
            }
        }
    }

    private fun likeLikeParent(
        parentId: String,
    ) {
        viewModelScope.launch {
            liker.clickLike(
                posts = likePagingState.value.items,
                parentId = parentId,
                onRequest = { isLiked ->
                    postUseCases.likeParentUseCase(
                        parentId = parentId,
                        parentType = ParentType.Post.type,
                        isLiked = isLiked,
                    )
                },
                onStateUpdate = { posts ->
                    _likePagingState.value = likePagingState.value.copy(
                        items = posts,
                    )
                },
            )
        }
    }

    private fun ownLikeParent(
        parentId: String,
    ) {
        viewModelScope.launch {
            liker.clickLike(
                posts = ownPagingState.value.items,
                parentId = parentId,
                onRequest = { isLiked ->
                    postUseCases.likeParentUseCase(
                        parentId = parentId,
                        parentType = ParentType.Post.type,
                        isLiked = isLiked,
                    )
                },
                onStateUpdate = { posts ->
                    _ownPagingState.value = ownPagingState.value.copy(
                        items = posts,
                    )
                },
            )
        }
    }

    fun getProfile(userId: String?) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            profileUseCases.getProfileUseCase(
                userId ?: getOwnUserIdUseCase(),
            ).apply {
                when (this) {
                    is Resource.Success -> {
                        val bitmap = try {
                            val url = URL(this.data?.bannerUrl)
                            val inputStream: InputStream = withContext(Dispatchers.IO) {
                                (url.openConnection() as HttpURLConnection).run {
                                    connect()
                                    inputStream
                                }
                            }
                            val bufferedInputStream = BufferedInputStream(inputStream)
                            BitmapFactory.decodeStream(bufferedInputStream)
                        } catch (e: IOException) {
                            e.printStackTrace()
                            null
                        } catch (e: HttpException) {
                            e.printStackTrace()
                            null
                        }
                        _state.value = _state.value.copy(
                            profile = this.data,
                            bitmap = bitmap,
                            isLoading = false,
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false)
                        _eventFlow.emit(
                            UiEvent.SnackBarEvent(this.uiText ?: UiText.unknownError()),
                        )
                    }
                }
            }
        }
    }

    private fun followUser(userId: String) {
        viewModelScope.launch {
            val isFollowing = state.value.profile?.isFollowing == true

            _state.value = _state.value.copy(
                profile = state.value.profile?.copy(isFollowing = !isFollowing),
            )

            profileUseCases.followUserUseCase(
                userId = userId,
                isFollowing = isFollowing,
            ).apply {
                when (this) {
                    is Resource.Success -> Unit
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            profile = state.value.profile?.copy(isFollowing = isFollowing),
                        )
                        _eventFlow.emit(
                            UiEvent.SnackBarEvent(uiText = this.uiText ?: UiText.unknownError()),
                        )
                    }
                }
            }
        }
    }
}
