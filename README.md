# Android-Game-Center-App
University of Toronto app develope project in Java 2018
readme file for group0705

#Android Studio Setup Instructions 

URL to clone: 
https://markus.teach.cs.toronto.edu/git/csc207-2018-09-reg/group_0461

1. Go to the Welcome Menu of Android Studio
2. Click on "check out project form Vision Control" and select
Git
3. Paste the URL and click test.
4. Once the connection is successful, click clone.
5. When it asks "Would you like to create an Android Studio project",
click yes.
6. On Import Project, select the "Import project from external model"
option then select Gradle. Next.
7. Click on "..." on the top right, go into your cloned folder,
go into Phase 1, select the GameCentre folder. Select the default
gradle wrapper. Finish.
(Or you simply add "\Phase1\GameCentre" for Windows;
"/Phase1/GameCentre" for Mac)
8. Let the project complete building.
9. For "Unregistered VCS root detected", simply click 
"Add root".

Viola! The project should be up and running.

Instructions when encountering "Unsupported Modules Detected":
1. Close the project and return to the Welcome Menu
2. Click "Open an existing Android Studio project"
3. Find the GameCentre, select it, and click OK.
("~\StudioProjects\group_####\Phase1\GameCentre" for Windows;
"~/StudioProjects/group_####/Phase1/GameCentre" for Mac)

If it still does not work, attempt a gradle sync.

IMPORTANT NOTICE : MAKE SURE TO CLEAR APP DATA BEFORE LAUNCHING

################################## APP INSTRUCTION ##########################################

Once the app is successfully launched, the user will see the
<LOGIN SCREEN>

	-Can either log-in with existing account or create a new account.
	note: Attempting to log-in with non-existing account will notify
     	      user with 'Incorrect username or password'. The user can 
	      attepmt login 5 times and after that user will be forced to
	      sign-up a new account. (number of attempts are shown on screen)
	-Clicking 'Sign-up' button will take the user to.. 

<NEW USER (SIGN-UP) SCREEN>

	Can create a new account with username and password
	note: can make multiple accounts but not duplicates (every user name is unique)
	note: If password do not match the confirm password the registration
	      will fail to prevent users having typo in their password and 
	      will notify the user
	note: Capitals do matter

Once the user successfully log-in with their account..

==========================================================================================
<STARTING SCREEN>

	They will be greeted with the welcome sign with their name at the top !
	(same for every Screen)
	There are many buttons and here is what they all do..

GAME A. SLIDING TILES : Go to Sliding Tiles Game Menu Screen

GAME B. CONCENTRATION : Go to Concentration Game Menu Screen

GAME C. SQUIRTLE : Go to Squirtle Game Menu Screen

D. PERSONAL RECORDS : Go to Personal Records Screen (Scoreboard)

==========================================================================================
A. <SLIDING TILES GAME MENU SCREEN>



1. PLAY : pressing Play button will show a pop-up with 3 options
 -> EASY(3X3) : Can start a new 9 (3x3) tiles game
	       Clicking this button will take the user to

	<GAME SCREEN>

 -> NORMAL(4X4) : Can start a new 16 (4X4) tiles game
	       Clicking this button will take the user to

	<GAME SCREEN>

 -> HARD(5X5) : Can start a new 25 (5X5) tiles game
	       Clicking this button will take the user to

	<GAME SCREEN>

2. LOAD SAVED GAME : Can load the last saved game
		     Note: Sliding Tile games are automatically saved
			   without manual save buttons. Saved every 1 second.
		     Clicking this button will take the user to

	<GAME SCREEN>

3. HOW TO PLAY : Takes user to 

	<How to play sliding tiles game Screen>

4. LEADERBOARD : Takes user to

	<LEADERBOARD SCREEN> 

===========================================================================================
<MAIN SCREEN = GAME SCREEN>

	This is the sliding tiles game
	Rules:
	 - each tile serve as a button
	 - user can only tap one tile at a time
	 - user can only move tiles that are adjacent to the blank tile
	 - tapping all other tiles will notify the user with 'invalid move'
	 - win by sorting all the tiles in order with blank tile being at the
	   very right bottom corner of the board in row - col order
	 -note: same rule applies for all sizes of board
	*UNDO FEATURE
	 - user can undo their moves any time during the game by clicking the
	   undo Button
	 - Undo will not increase the score but will not reduce it either
	 - User can toggle between two Undo modes. When the switch at the
	   right top corner is On, the undo becomes default undo which means
	   user can only undo up to 3 times. Turning this switch off allow
	   user to undo unlimited times until beginning state of the game.
	   Note: user can toggle between default and unlimited undo anytime
		 during the game.
	
	Score:
	 - calculated with number of moves (less the better)
	 - In scoreboard, best score only gets calculated if the user wins
	 - Current score is displayed at the top left corner
	 - Each account will only store one best score for sliding tiles
	 - Solving the game will display with a message depending and related to
	   last score.

	Note: The game will get autosaved every 1 second
	      You can still undo anymoves on loaded games

<How to play sliding tiles game Screen>

	Detailed and simplified Instruction Screen for users to read

<LEADERBOARD>

	Show top 5 best score( in this game user with lowest score will take 1st place )
	in order from top to bottom.

=========================================================================================
B. <CONCENTRATION GAME MENU SCREEN>

	
	
1. PLAY : pressing Play button will show a pop-up with 2 options
 -> SINGLE PLAYER : Can start a game with single player mode. Pressing the 
	 	    button will take the user to..

	<FLIPPINGGAME SCREEN>

 -> TWO PLAYERS : Can start a new two players mode
	       Clicking this button will take the user to

	<FLIPPINGGAME SCREEN>

2. LOAD SAVED GAME : Can load the last saved game
		     Note: Concentration games are automatically saved
			   without manual save buttons. Saved every 1 second.
		     Clicking this button will take the user to

	<FLIPPINGGAME SCREEN>

3. HOW TO PLAY : Takes user to 

	<How to play sliding tiles game Screen> -  Friendly instruction for users to 
						   quickly learn how to play


4. LEADERBOARD : Takes user to

	<LEADERBOARD SCREEN>

==========================================================================================

<FLIPPINGGAME SCREEN>

	This is the Concentration/Flipping game
	Rules For Single Player:
	 - there are 12 faced-down cards(also buttons) and 6 matching pairs
	 - there is a timer for user to check their current time
	 - user can take unlimited time to finish the game
 	 - user can tap only the cards
	 - tapping one of the cards ( first move) will show the face side of 
	   the card to the user (until user plays their second move)
	 - first move and second move cannot be same card
 	 - user will try to guess matching pairs with first move and second move
	   and **win by matching everysingle pairs. And also memorizing the moves
	   he failed to match via Concentration game.
	 
	*PEEK FEATURE  (peek button at the top right corner)
	 - Replaced Undo feature as undo feature is unreasonable for this game.
	   We could have tried undo first move but that would be cheating since
	   player can do that for every cards and see. It wouldnt make sense in 
	   two player mode either because matched cards should dissapear once
	   matched with correct pair. Returning matched cards don't make much
	   sense and make the game not enjoyable. 
	 - player can peek up to 2 times in single player mode
	 - player cannot use peek feature in two players mode
	 - will not affect the score
	
	
	Score:
	 - calculated by how fast user wins the game(less time the better)
	 - In scoreboard, best score only gets calculated if the user wins
	 - Current score is displayed at the top beside the peek button
	 - Each account will only store one best score for concentration game
	 - Solving the game will display with a message depending and related to
	   last score.

	Note: The game will get autosaved every 1 second
 
=========================================================================================
D.<SQUIRTLE GAME MENU SCREEN>

PLAY: pressing the PLAY button will show a pop up with 3 options
EASY: can start a game with the objects falling down at a relatively slow speed,
 pressing the button will bring the user to <NEWGAME SCREEN>
MEDIUM: can start a game with the objects falling down at a relatively moderate 
speed, pressing the button will bring the user to <NEWGAME SCREEN>
HARD: can start a game with the objects falling down at a relatively fast speed, 
pressing the button will bring the user to <NEWGAME SCREEN>

#not:e that even the objects falling speed are different, the squirtle that the 
user control is always at the same speed

HOW TO PLAY: clicking this button will bring the user to <INSTRUCTION SCREEN> - 
general instructions for the user to learn how to play the game

LEADERBOARD: Bring user to <LEADERBOARD SCREEN>

===========================================================================================

<SQUIRTLE GAME SCREEN>

START: clicking the button will initiate the game
QUIT: clicking the QUIT button will bring the user back to the <SQUIRTLE GAME MENU SCREEN>
High Score: the highest score of the player will also be displayed on this screen in a textview
		
		This is the rule for the Squirtle game:
-A timer will be started once the game is started
-Water drop(blue), fireball(red) and Pikachu token(yellow) will be falling down randomly 
   from the top to the bottom
-The user can control the Squirtle to move horizontally on the bottom of the board
-The idea of the game is to collect as much Pikachu token as possible
-Every time we collected one Pikachu token then we will earn 10 points
-Since Squirtle is a turtle, hit by the fireball will cause damage to it! Therefore 
the board will be narrow down once the Squirtle is hit
-The only way to save the Squirtle is to collect the water drop to heal it
-Collecting the water drop will broaden the board
-The game is over when the board is smaller than the size of the Squirtle
-A pop-up message will be displayed to tell the user how long they survived in the game and 
will quickly bring them back to the menu
-Try your best to save the Squirtle and see how many points you can get!!




























