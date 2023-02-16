# Share Block
Methodology
This is a full-stack project based on android jetpack compose and Ktor with clean architecture 
and solid code principles. The detailed technologies are as below.

(a) Frontend

1) Network:
2) Retrofit is used for HTTP requests.
3) Gson for the retrofit convertor.
4) Ktor client and Scarlet are for the WebSocket client.
5) One signal is for push notifications.

(b) Ui Design

1) Jetpack Compose Libraries.
2) Compose Navigation.
3) The coil is for image requests.
4) Dagger-Hilt is for dependency injection.
5) Paging is for paged server data.
6) Swiperefresh is for refreshing the page.
7) Exoplayer and media3 are for the video player.
8) GitHub open-source crop tool is for cropping images.

(c) Ktor Backend

1) WebSocket
2) Jwt Authentication
3) Content Negotiation
4) HTTP Schemas
5) Serialisation
6) MongoDB Database
7) Koin Dependency Injection
8) One Signal Push Notification


Once open the app, we will see the splash screen, which is used to prepare necessary 
data, such as the Jwt token in this app. Then we will see the login page. A new user is 
required to register an account. Just click the signup string with the blue colour below, and 
you will see the registration page. Entered the register page, you can see the edit lines. You 
need to enter three necessary strings, click the register button, and we are good to go.
Redirecting to the login page, enter the email and password you have registered, and then 
you will be turning to the home screen.

The home screen is used to show the posts you and the people you followed have 
created. You can double-tap the postcard, and then you can see a red heart. The heart switch 
below will be selected. Of course, you can also set the heart switch to show you like the post.
You can also click the comment button to add a comment or click the share icon to share the 
post with others. Once you click the postcard, you will be directed to the post detail page, you 
can make all the motions with the post just like them on the home screen, and you can see 
the comments. 

Back to our home page, click the search icon at the top, and you can see the search 
screen, which is used to search for people and make the following motion. Also, you can click 
the user card, and you will be directed to the user profile screen.

Then click the second icon in the bottom navigation, and you will see the chat screen 
showing the chat activities with others. You can click the chat card and then see the message 
screen. You can send the message on this screen. Also, if you click the profile picture, you 
can be directed to the profile screen. The round adds a button in the centre of the navigation 
bar to create a post. You can add descriptions and images or videos. And also, you can take 
a photo and then create a post with the picture you have made. Click the post button at the 
top and complete the post. 

The third icon in the navigation bar is activity. You will see the information about others 
who have interacted with you.

The last icon in the navigation bar, you will see the profile. On this page, you can edit 
your profile. Just click the menu in the top bar or log out. The tab layout allows you to see the 
posts you have created and the comments you have made. Also, you can see the posts you 
have liked.

### Scan the QR code below to download the app
### ![QR code](/app/src/main/res/drawable/share_block_qr.png)
