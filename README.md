DO NOT COMMIT TO MASTER BRANCH
Create a branch in your name and commit there, I'll merge branches later.
it works better that way, since you can update your code and commit without any issues


Alright guys first merge is done! Ignacio's wolves are now integrated into the master game.

Andrew, I'm missing the minimap class from you so I can't merge you in just yet.

I'm still working on figuring out how to get input for sheep and wolf locations 

-Erick

SheepDieGameKills
=================
Game Requirements:

1. Reading from input file - xavier++++++++++++++++++++++++++++++++++++++++++

     
3. GUI enhancements - Andrew+++++++++++++++++++++++++++++++++++++++++++++++++

4. reimplement fractal grass+++++++++++++++++++++++++++++++++++++++++++++++++

    Ignacio++++ Talked to tran after class and he said we dont have to implement fractal grass if we didnt want to.


6. be able to input location for sheep and wolf(I'm working on this now - Erick)++++++++++++++++++++++++++++++


99.  When are we going to link Merge everything in order to ensure that everything is working smoothly?????? 




What Is Done: ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

2. Priority Queue algorithm on the wolf - Ignacio++++++++++++++++++++++++++++

    Ignacio+++++ almost done with the wolf just have a small problem where they are not hunting seperately
                        
                    thinking of making it so that you can add sheep to the board at anyMoment but still have to think
                    about how to make that happen. 
                    
                    also made the wolfs thread not end they continue moving even after the game is over...
                    this will help if we add more sheep then the wolfs will go and hunt without
                    having to make them hunt again.                    
                    
5. sheep that move+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    Ignacio++++ Sheep move when wolf are in their range.....

7. Each sheep/wolf must be in it's own separate thread+++++++++++++++++++++++

    Ignacio++++ They are already running on their own thread so we dont have to worry about this.
                 as they are created once you throw the run or hunt button they they are all thrown into their own thred
                
                 Sheeps thread is called "squirm" & wolfs is called "hunt"

COMMENTS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    Ignacio+++++ We should try to have all these branches merge as one by tomorrow just to make sure everything 
                is running good. last thing we need is having great program and when we merge it all crashes.
                
                
                
STUPID COMMENTS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    Ignacio+++++ Hey can we change the "HUNT" button to "RELEASE THE HOUNDS" or "I LET THE DOGS OUT"
                just to have a laugh lol.
                
                made it so that the wolfs still move at the end
                the game is not over when sheep die. wolfs just move around 
                playing tag with each other. the pink one is it if you cant tell............ 
                
                also if you hit the HUNT button one of the Sheep running in the background gets revived.
