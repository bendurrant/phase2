# phase2 Ben Durrant Josh Nelson Group13



Anytime the user needs to choose from a list they enter the number of the row they wish to select. Everything is prompted and nothing should need to be assumed for input. Anytime we read in a number an integer should be supplied. To simplify our number control we don't use doubles. At these prices +- a dollar to round isn't that big of a deal we feel. 
 

Users can log in with an existing username and password or create a new one. 

2 example existing are (login: Ben Durrant, Password: YankeeDoodle) and (login JohnDoe, Password: hunter2) 


Our admins are able to do everything a regular user can but also has access to secret options that a normal user can not do.
The admin account is (login: ADMIN1, Password: ADMIN1) 

Users are able to create and edit their own THs. When creating a th the user does not add availabilities or keywords.
Instead this information is added when the user chooses to edit their th. Other basic info is what is added when creating a TH. (e.g name, phone,address, etc)


Degrees of separation takes in two user ID's and prints either yes or no depending on if they are two degrees apart. Any user can do this, not just admins.


Whenever we calculate an average score we do not display that average score but order by the score. So the user does not see the average score but sees them in order.


To add a reservation the user must first browse and then select the TH they wish to reserve.



 This apllies to all options specific to another TH.

For your testing of the recomendations (and our debugging) we have created a small network of dependencies between 3 users. Feel free to play with them and reserving certain THs
(The prompt appears after a TH is added to the cart)

User1 has visits in thid 9,5
JoshNelson has visits in 9,8
JohnDoe has visits in 9,8,7

For searching purposes here are the relevant categories: 
5 is an apartment
7 is an SFH
8 is an SFH
9 is a condo

To login to these accounts:
(login: user1, Password: user1)
(login: JoshNelson, Password: password)
(login JohnDoe, Password: hunter2) 



As for data to play with we tried to leave a range of data that covers empty cases, full and in between.
But of course feel free to create new users and edit things as you need.