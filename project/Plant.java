package project;

import java.awt.Color;
import java.util.List;

import game.Direction;
import naturesimulator.Action;
import naturesimulator.LocalInformation;
import ui.GridPanel;
/**
 * Class representing an creature whose max health is 1.00.
 * Also initial health of an Herbivore is 0.5. Has its own action methods.
 * Describe the abstract methods of Creature class.
 * @author Halil Umut Özdemir
 */
public class Plant extends Creature{

	private static final double MAX_HEALTH = 1.00;
	/**
     * Creates new Plant object with its health and location.
     * Constructor is private because only this class can create a object with specific health. 
     * @param health the current health.
     * @param x the x component of current location.
     * @param y the y component of current location.
     */
	private Plant(double health, int x, int y) {
		super(health, x, y);
	}
	/**
     * Creates new Plant object with only location. Set health to 0.5.
     * Constructor is public because other classes can create a herbivore with health 0.5. 
     * @param x the x component of current location.
     * @param y the y component of current location.
     */
	public Plant(int x, int y) {
		super(0.5, x, y);
	}
	/**
     * Draws the Plant to the panel. 
     * The green color become darker with decreasing health.
     * @param panel the panel which we draw our creatures.
     */
	@Override
	public void draw(GridPanel panel) {
		panel.drawSquare(this.getX(), this.getY(), new Color(0,(int)((210/MAX_HEALTH)*this.getHealth())+45,0));
	}
	/**
     * An instance method of Plant.
     * Creates new Plant with necessary health level.
     * New plant place is determined by direction parameter.
     * @param direction determines the place of new herbivore.
     * @return the new plant with its own health and location. Last return(null) is only to escape compiler errors.
     */
	@Override
	public Creature reproduce(Direction direction) {
		double newPlantHealth = this.getHealth()*0.1;
		this.setHealth(this.getHealth()*0.7);
		if(direction==Direction.UP) {
			return new Plant(newPlantHealth,this.getX(),this.getY()-1);
		}
		if(direction==Direction.DOWN) {
			return new Plant(newPlantHealth,this.getX(),this.getY()+1);
		}
		if(direction==Direction.LEFT) {
			return new Plant(newPlantHealth,this.getX()-1,this.getY());
		}
		if(direction==Direction.RIGHT) {
			return new Plant(newPlantHealth, this.getX()+1,this.getY());
		}
		return null;
	}
	/**
     * An instance method of Plant.
     * Increases the health of plant by 0.05.
     */
	@Override
	public void stay() {
		this.setHealth(this.getHealth()+0.05);
	}
	/**
     * An instance method of Plant.
     * Decides the current action of Plant.
     * @return returns the most logical Action.
     * @param information the local information(creatures around it etc.) of current plant.
     */
	@Override
	public Action chooseAction(LocalInformation information) {
		if(this.getHealth()>=0.75) {
			List<Direction> possibleSpaces = information.getFreeDirections();
			if(!possibleSpaces.isEmpty()) {
				return new Action(naturesimulator.Action.Type.REPRODUCE,LocalInformation.getRandomDirection(possibleSpaces));
			}
		}
		return new Action(naturesimulator.Action.Type.STAY);
	}
	/**
     * A setter method of Plant.
     * Sets the health of Plant.Health value has to be between 0.00 and 1.00.
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
