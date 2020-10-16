package pl.marcindebski.escape.levels;

import pl.marcindebski.escape.Escape;

public class LevelRunner
{

    private Level level;

    public void setLevel(Level level)
    {

	this.level = level;
    };

    public void run()
    {

	level.runGameLogic();
	level.renderPrimitives();

	Escape.getBatch().begin();
	level.renderBath();
	Escape.getBatch().end();

    };

}
