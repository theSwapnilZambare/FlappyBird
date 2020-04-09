package com.mygame.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] birds;
	Texture gameover;
	Circle birdcircle;
	Rectangle[] toprectangle,bottomrectangle;
	ShapeRenderer shapeRenderer;
	int birdstate=0;
	float birdY=0;
	float velocity=0;
	int gameState=0;
	int score=0;
	int scoringTube=0;
	float gravity = 2;
	Texture topTube, bottomTube;
	float gap=400;
	float maxTubeOffset;
	Random random;
	float[] tubeOffset;
	float tubevelocity=4;
	float[] tubeX;
	int numberofTube=4;
	BitmapFont font;
	float distanceBetweenTubes;
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		birds = new Texture[2];
		birdcircle = new Circle();
		shapeRenderer = new ShapeRenderer();
		birds[0]=new Texture("bird.png");
		birds[1]=new Texture("bird2.png");

		topTube=new Texture("toptube.png");
		bottomTube=new Texture("bottomtube.png");
		maxTubeOffset= Gdx.graphics.getHeight()/2- gap/2 -100;
		random = new Random();
		distanceBetweenTubes= Gdx.graphics.getWidth()/2;
		tubeX= new float[numberofTube];
		tubeOffset=new float[numberofTube];
		toprectangle=new Rectangle[numberofTube];
		bottomrectangle=new Rectangle[numberofTube];
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);
		gameover= new Texture("gameover.png");

		gamestart();
	}

	public void gamestart(){
		birdY= Gdx.graphics.getHeight()/2 - birds[0].getHeight()/2;
		for(int i=0;i<numberofTube;i++)
		{

			tubeX[i]= Gdx.graphics.getWidth()/2- topTube.getWidth()/2 + Gdx.graphics.getWidth()+ (i*distanceBetweenTubes);
			toprectangle[i]=new Rectangle();
			bottomrectangle[i]=new Rectangle();

		}
	}
	@Override
	public void render () {

		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		batch.end();
	if(gameState==1)
	{
		if(tubeX[scoringTube]<Gdx.graphics.getWidth()/2)
		{
			score++;
			if(scoringTube<numberofTube-1)
			{
				scoringTube++;
			}
			else{
				scoringTube=0;
			}
		}


			if (Gdx.input.justTouched()) {

				velocity = -30;


			}


		for(int i=0;i<numberofTube;i++)
		{

			if(tubeX[i]<- topTube.getWidth())
			{
				tubeX[i]+= (numberofTube)*distanceBetweenTubes;
				tubeOffset[i]= (random.nextFloat()- 0.5f)*(Gdx.graphics.getHeight()-gap-200);
			}
			else {
				tubeX[i] = tubeX[i] - tubevelocity;

			}
			batch.begin();

			batch.draw(topTube, tubeX[i],Gdx.graphics.getHeight()/2 +gap/2 +tubeOffset[i]);
			batch.draw(bottomTube,tubeX[i],Gdx.graphics.getHeight()/2 -(gap/2+ bottomTube.getHeight() - tubeOffset[i]));//+ tubeOffset

			toprectangle [i]=new Rectangle( tubeX[i],Gdx.graphics.getHeight()/2 +gap/2 +tubeOffset[i],topTube.getWidth(),topTube.getHeight());
			bottomrectangle[i]=new Rectangle(tubeX[i],Gdx.graphics.getHeight()/2 -(gap/2+ bottomTube.getHeight() - tubeOffset[i]),bottomTube.getWidth(),bottomTube.getHeight());

			batch.end();

		}



		if(birdY>0 )
		{
			velocity = velocity+gravity;
			birdY-=velocity;// birdY=-velocity

		}
		else if(birdY<=0)
		{
			gameState=2;
		}

		if(birdY> (Gdx.graphics.getHeight() - birds[0].getHeight()/2))
		{
			birdY= Gdx.graphics.getHeight() - birds[0].getHeight()/2;
		}
	}

	else if(gameState==0){
		if (Gdx.input.justTouched()) {

			gameState = 1;

		}
	}
	else if(gameState==2)
	{	batch.begin();
		batch.draw(gameover,Gdx.graphics.getWidth()/2-gameover.getWidth()/2,Gdx.graphics.getHeight()/2-gameover.getHeight()/2);
		batch.end();

		if (Gdx.input.justTouched()) {
			gameState = 1;
			gamestart();

			scoringTube=0;
			score=0;
			velocity=0;


		}
	}


		if(birdstate==0)
		{
			birdstate=1;
		}
		else{
			birdstate=0;
		}

batch.begin();
		batch.draw(birds[birdstate],Gdx.graphics.getWidth()/2 - birds[birdstate].getWidth()/2,birdY);
		font.draw(batch,String.valueOf(score),100,200);
		batch.end();
		birdcircle.set(Gdx.graphics.getWidth()/2,birdY + birds[birdstate].getHeight()/2, birds[birdstate].getWidth()/2);
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);

		//shapeRenderer.circle(Gdx.graphics.getWidth()/2,birdY + birds[birdstate].getHeight()/2, birds[birdstate].getWidth()/2);


		for(int i=0 ; i<numberofTube;i++)
		{
			//shapeRenderer.rect(tubeX[i],Gdx.graphics.getHeight()/2 +gap/2 +tubeOffset[i],topTube.getWidth(),topTube.getHeight());
			//shapeRenderer.rect(tubeX[i],Gdx.graphics.getHeight()/2 -(gap/2+ bottomTube.getHeight() - tubeOffset[i]),bottomTube.getWidth(),bottomTube.getHeight());
			if(Intersector.overlaps(birdcircle,toprectangle[i]) || Intersector.overlaps(birdcircle,bottomrectangle[i]))
			{
				gameState=2;
			}
		}
		//shapeRenderer.end();



	}
	@Override
	public void dispose () {

	}
}
