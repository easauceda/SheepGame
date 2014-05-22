------Running the the SheepWolf.jar
you need to have these files next to the SheepWolf.jar in order for it to run.
graphFile.txt
input.txt

-----------running the SheepWolf.jar----------------------------------
input into terminal "java -jar SheepWolf.jar" when you are in the directory
that SheepWolf.jar is located in and it should run. 

make sure to run the wolfs first and then the sheep as the sheeps require to
know the wolfs location for dying purposes.
---------------------------------------------------------------------

----------the input.txt is what configures the eviroment.-----------

"board width" 
controls the width.(still being worked on)

"board height"
controls the height.(still being worked on)

"clock" 
controls the game speed. (set to 1000 as the server is slow at handling
messages)

"sheep count" 

sets the number of sheeps.

----------------------IMPORTANT -----------------------------------------------
you need wolfs to be able to have the sheeps move.

if playing over a network remember to set the ip address to the user running the
wolfs and make sure your ports are matching.

-------------------------------------------------------------------------------

"wolf count" sets the number of wolfs (wolfs begin to play tag when their are no
 sheep present)

"ip" this should be set to the ip address of the person controlling the wolfs if
you are running the wolfs then set it to localhost.

"port" 

decides which port you will be using to comunicate over a network and on 
localhost as sheep and wolfs clients are running on seperate threads they need 
to chat with each other.

"nickname" 

is important as this gives a name to the sheep you are controlling.
if two people have the same nickname it will through the system off.


----------------------IMPORTANT -----------------------------------------------
change the nickname if you are playing over a network with others.
-------------------------------------------------------------------------------
"graphFile.txt" 

this can be changed to other names like "node.txt" but make sure
you have nodes and edges as sheep need places to move in this game. the game
looks for the .txt to try and know where to look for the graphs.

"WolfChatOn" 

can be placed with out the "#" and will start up the chatBox as
Wolfserver any commands you throw in there will be interpreted as if you were a 
wolf stating your position or kill.

(I used this for debugging and positioning wolfs where I wanted them, yet also
to mess up other classmembers programs in class so please,try to play nice). 

----------the graphFile.txt is what configures the eviroment.-----------
the graphFile.txt is what gives the nodes and edges that the sheep can travel
along the map. This one is very simple......

N is to state its going to be a node and each node has or doesnt have edges.
Edges that are "E" are connections between each node.

entry example ---------------------------------------
 N "number_Unique" "Position_in_X" "Position_in_Y"
------------------------------------------------------

"number_Unique"

 has to be a unique number for this specific node as this is the
its specific name to be used to link with other nodes thru the edges.


E is used to identify edges and specify who they are connecting together node1
to node2 and the weight.

entry example------------------------------------------------
E "Node_unique_name_1" "Node_unique_name_2" "Weight_of_node" 
-------------------------------------------------------------

(Weight is not used 
but maybe used for sheep to make decisions on which edge to use.)

----------------Multiplayer over a network with other people------------------

ip = "ip_of_person_with_wolfs" this needs to be set 

port = "the_port_you_want_to_talk_thru" this needs to be the same as the other 
players you want to play with

nickname = "Unique_name_for_each_player" this has to be unique or the server
will not be able to tell apart each players sheep.

