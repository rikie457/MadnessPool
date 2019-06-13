Tips
------------------------------------------------------------------
- overall low quality of documentation or no documentation
- code structure wise there are some serious issues see Ball, Game, mostly:
	- not using arrays/lists where you should leads to a LOT of code duplication
	- not retrieving a reference to an object ONCE and storing it in a local variable, but doing eg 100's of balls.get(i)
	  again a LOT of code duplication
	- very deeply nested if/for statements:
		- use early returns instead
		- use the && operator to combine checks
- Try to optimize your collision loops in order to optimize the framerate, the issue with your current code structure is that it is so hard to read, that performance bottlenecks do not automatically 'highlight' themselves. But one thing is certain you can at least reduce your checks by a factor 0.5. Meaning you go from 256 checks per frame to 128.
Another thing based on your ball xy you can skip checking all the pockets alltogether, saves you 8 checks per ball.
Also see the code comments.

Tops
-------------------------------------------------------------------
Nice complex game, good challenge coding wise ;))


