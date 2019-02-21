package project;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import game.Direction;
import naturesimulator.Action;
import naturesimulator.LocalInformation;
import ui.GridPanel;

/**
 * Class representing an creature whose max health is 20.00.
 * Also initial health of an Herbivore is 10.00. Has its own action methods.
 * Describe the abstract methods of Creature class.
 * @author Halil Umut Özdemir
 */
public class Herbivore extends Creature {
	
	private static final double MAX_HEALTH = 20.00;
	/**
     * Creates new Herbivore object with its health and location.
     * Constructor is private because only this class can create a object with specific health. 
     * @param health the current health.
     * @param x the x component of current location.
     * @param y the y component of current location.
     */
	private Herbivore(double health, int x, int y) {
		super(health, x, y);
	}
	/**
     * Creates new Herbivore object with only location. Set health to 10.00.
     * Constructor is public because other classes can create a herbivore with health 10.00. 
     * @param x the x component of current location.
     * @param y the y component of current location.
     */
	public Herbivore(int x, int y) {
		super(10.0,x,y);
	}
	/**
     * Draws the Herbivore to the panel. 
     * The red color become darker with decreasing health.
     * @param panel the panel which we draw our creatures.
     */
	@Override
	public void draw(GridPanel panel) {
		panel.drawSquare(this.getX(), this.getY(), new Color((int)((180/MAX_HEALTH)*this.getHealth())+75,0,0));	
	}
	/**
     * An instance method of Herbivore.
     * Creates new Herbivore with necessary health level.
     * New herbivore place is determined by direction parameter.
     * @param direction determines the place of new herbivore.
     * @return the new herbivore with its own health and location. Last return(null) is only to escape compiler errors.
     */
	@Override
	public Creature reproduce(Direction direction) {
		double newHerbHealth = this.getHealth()*0.2;
		this.setHealth(this.getHealth()*0.4);
		if(direction==Direction.UP) {
			return new Herbivore(newHerbHealth,this.getX(),this.getY()-1);
		}
		if(direction==Direction.DOWN) {
			return new Herbivore(newHerbHealth,this.getX(),this.getY()+1);
		}
		if(direction==Direction.LEFT) {
			return new Herbivore(newHerbHealth,this.getX()-1,this.getY());
		}
		if(direction==Direction.RIGHT) {
			return new Herbivore(newHerbHealth, this.getX()+1,this.getY());
		}
		return null;
	}
	/**
     * An instance method of Herbivore.
     * Reduces the health of herbivore by 0.1.
     */
	@Override
	public void stay() {
		this.setHealth(this.getHealth()-0.1);
	}
	/**
     * An instance method of Herbivore.
     * Decides the current action of Herbivore.
     * @return returns the most logical Action.
     * @param information the local information(creatures around it etc.) of current herbivore.
     */
	@Override
	public Action chooseAction(LocalInformation information) {
		if(this.getHealth()==MAX_HEALTH) {
			List<Direction> freeMoves = information.getFreeDirections();
			if(!freeMoves.isEmpty()) {
				Direction direction = LocalInformation.getRandomDirection(freeMoves);
				return new Action(naturesimulator.Action.Type.REPRODUCE,direction);
			}
		}
		ArrayList<Direction> enemyDirections = new ArrayList<Direction>();
		if(information.getCreatureDown() instanceof Plant) {
			enemyDirections.add(Direction.DOWN);
		}
		if(information.getCreatureUp() instanceof Plant) {
			enemyDirections.add(Direction.UP);
		}
		if(information.getCreatureLeft() instanceof Plant) {
			enemyDirections.add(Direction.LEFT);
		}
		if(information.getCreatureRight() instanceof Plant) {
			enemyDirections.add(Direction.RIGHT);
		}
		if(!enemyDirections.isEmpty()) {
			Direction direction = LocalInformation.getRandomDirection(enemyDirections);
			return new Action (naturesimulator.Action.Type.ATTACK,direction);
		}
		if(this.getHealth()>1.0) {
			List<Direction> freeMoves = information.getFreeDirections();
			if(!freeMoves.isEmpty()) {
				return new Action(naturesimulator.Action.Type.MOVE,LocalInformation.getRandomDirection(freeMoves)) ;
			}
		}
		return new Action(naturesimulator.Action.Type.STAY);
	}/**
     * An instance method of Herbivore.
     * Can be used to attack.Parameter has to be Plant. Herbivore can only attack to a Plant.
     * After changing the location, increases the health by the health of parameter.
     * Then set the health of parameter to 0.0.
     * @param creature the creature which is attacked by current Herbivore.
     */
	@Override
	public void attack(Creature creature) {
		if(creature instanceof Plant) {
			this.setHealth(this.getHealth()+creature.getHealth());
			super.attack(creature);
		}
	}
	/**
     * An instance method of Herbivore.
     * Can be used to move a herbivore.(Plants can not move.)
     * After changing the location, decreases health by 1.00.
     * @param direction the movement direction of Herbivore.
     */
	@Override
	public void move(Direction direction) {
		super.move(direction);
		this.setHealth(this.getHealth()-1.00);
	}
	/**
     * A setter method of Herbivore.
     * Sets the health of Herbivore.Health value has to be between 0.00 and 20.00.
     * @param health the new value of health.
     */
	@Override
	protected void setHealth(double health) {
		if(health>=MAX_HEALTH) {
			super.setHealth(MAX_HEALTH);
		}else if(health>0) {
			super.setHealth(health);
		}else {
			super.setHealth(0.0);
		}
		
	}
}
