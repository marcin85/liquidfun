package pl.marcindebski.escape.levels.hud;

import pl.marcindebski.escape.Escape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hud
{

    private OrthographicCamera camera;

    public OrthographicCamera getCamera()
    {
	return camera;
    }

    private Texture hudTexture;
    private Sprite hudSprite;

    public Hud()
    {

	hudTexture = new Texture(Gdx.files.internal("data/hud.png"));
	hudTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	hudSprite = new Sprite(hudTexture);
	hudSprite
		.setOrigin(hudSprite.getWidth() / 2, hudSprite.getHeight() / 2);
	hudSprite.setPosition(Escape.VIRTUAL_WIDTH / 2 - hudSprite.getWidth()
		/ 2, 30);

    }

    public void createCamera()
    {

	camera = new OrthographicCamera();
	camera.setToOrtho(false, Escape.VIRTUAL_WIDTH, Escape.VIRTUAL_HEIGHT);

    }

    public void render(SpriteBatch batch)
    {
	hudSprite.draw(batch);
    }

}
