package project;

import game.Direction;
import game.Drawable;
import naturesimulator.Action;
import naturesimulator.LocalInformation;

/**
 * Class representing the health and location information in its fields.
 * Also it has some getter, setter and some action methods.
 * Fields are private because I don't want random access. 
 * @author Halil Umut Özdemir
 */
public abstract class Creature implements Drawable {
	private double health;
	
	private int x,y;
	
	/**
     * Defines the initial values of fields of any type of its childs.
     * Constructor is protected because it must be accessible for only child. 
     * @param health the current health.
     * @param x the x component of current location.
     * @param y the y component of current location.
     */
	protected Creature(double health,int x,int y) {
		this.health=health;
		this.x=x;
		this.y=y;
	}
	/**
     * Getter for the health.
     * Can be used to assess the current health of a creature.
     * @return the current health of creature.
     */
	public double getHealth() {
		return health;
	}
	/**
     * Setter for the health. 
     * Setter is protected because only a child can set the health.
     * Can be used to change the health of a creature.
     * @param health the new value of the heath of the creature.
     */
	protected void setHealth(double health) {
		this.health=health;
	}
	/**
     * Getter for the x.
     * Can be used to assess the current x of a creature.
     * @return the current x of creature.
     */
	public int getX() {
		return x;
	}
	/**
     * Setter for the x.
     * Can be used to change the x of a creature.
     * Setter is protected because only a child can set the x.
     * @param x the new x value of creature.
     */
	protected void setX(int x) {
		this.x = x;
	}
	/**
     * Getter for the y.
     * Can be used to assess the current y of a creature.
     * @return the current y of creature.
     */
	public int getY() {
		return y;
	}
	/**
     * Setter for the y.
     * Can be used to change the y of a creature.
     * Setter is protected because only a child can set the y.
     * @param y the new y value of creature.
     */
	protected void setY(int y) {
		this.y = y;
	}
	/**
     * An instance method of Creature.
     * Can be used to move a creature which is Herbivore.(Plants can not move.)
     * @param direction the movement direction of Herbivore.
     */
	public void move(Direction direction) {
		if (this instanceof Herbivore) {
			if(direction == Direction.UP) {
				this.y= this.y-1;
			}else if(direction == Direction.DOWN) {
				this.y=this.y+1;
			}else if(direction == Direction.LEFT) {
				this.x=this.x-1;
			}else if(direction == Direction.RIGHT) {
				this.x=this.x+1;
			}
		}
	}
	/**
     * An instance method of Creature.
     * Can be used to attack.Only a herbivore can attack.(Plants can not attack.)
     * Parameter has to be Plant. Herbivore can only attack to a Plant.
     * Sets the location only.Then set the health of parameter to 0.0.
     * @param creature the creature which is attacked by current Herbivore.
     */
	public void attack(Creature creature) {
		if((this instanceof Herbivore)&&(creature instanceof Plant)) {
			this.x=creature.getX();
			this.y=creature.getY();
			creature.setHealth(0.0);
		}
	}
	/**
     * An abstract method of Creature.
     * All types of creatures can reproduce. But the way that they reproduce is different.
     * So this methods are implemented at child classes.
     * @param direction the direction of reproduction.
     */
	public abstract Creature reproduce(Direction direction);
	/**
     * An abstract method of Creature.
     * All types of creatures can stay. But the way that they stay is different.
     * So this methods are implemented at child classes.
     */
	public abstract void stay();
	/**
     * An abstract method of Creature.
     * All types of creatures have to pick an Action. But the way that they decide is different.
     * So this methods are implemented at child classes.
     * @param information the local information of current creature.
     */
	public abstract Action chooseAction(LocalInformation information);
		
}
