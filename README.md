# Fetching data from Django and showing it in the Recycler view

  **How to run**
  - After cloning the repo ```cd Bookstore```
  - If you want to run from mobile ```go to settings.py``` in ```ALLOWED_HOSTS``` write your ```wireless adapter IPv4 address```
  - Run the Django server using this command ```python manage.py runserver 0.0.0.0:8000```
  - Now open the ```LittleLemon``` on ```Android Studio```
  - Make sure to replace the ```baseUrl``` in ```LoginPage, RecyclerPage``` with your IPv4.
    - If you want to run it from the emulator replace the ```baseUrl``` with ```10.0.2.2```
    - If you want to run it from mobile replace the ```baseUrl``` with your ```laptop Wireless IPv4 address``` and make sure that your ```laptop and mobile are connected to the same internet```
      
  **How it works**
  - It contains the ```login authentication``` from the Django server
  - After login if the credentials are ```correct``` user is redirected to the ```Recycler Page``` where the ```data stored in the database``` is shown
  - On pressing the ```refresh``` button on the ```top right``` corner the data is fetched from the ```Django``` and stored in the ```Local Database``` if new data is inserted from the server it should be displayed in the Recycler Page.