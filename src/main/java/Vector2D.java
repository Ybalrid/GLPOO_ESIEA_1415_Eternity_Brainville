package main.java;

/**
 * Base class for vector calculations.
 */

public class Vector2D implements Comparable<Vector2D> {

	public float x;
	public float y;

	public Vector2D() {
		
		this.x = 0;
		this.y = 0;
	}

	public Vector2D (float x, float y) {
		
		this.x = x;
		this.y = y;
	}

	public float[] getCoordinates () {
		return new float[] { this.x, this.y };
	}

	/**
	 * Returns a vector with x coordinate set to 0.
	 */
	public Vector2D keepX () {
		return new Vector2D(this.x, 0f);
	}
	
	/**
	 * Returns a vector with y coordinate set to 0.
	 */
	public Vector2D keepY () {
		return new Vector2D(0f, this.y);
	}
	
	/** 
	 * return the opposite vector.
	 */
	public Vector2D opposite () {
		return new Vector2D(-this.x, -this.y);
	}
	
	/**
	 * Returns a new vector with inverted x.
	 */
	public Vector2D invertX () {
		return new Vector2D(-this.x, this.y);
	}
	
	/**
	 * Returns a new vector with inverted y.
	 */
	public Vector2D invertY () {
		return new Vector2D(this.x, -this.y);
	}
	
	/**
	 * Returns a new vector with swapped coordinates
	 */
	public Vector2D swap () {
		return new Vector2D(this.y, this.x);
	}
	
	/**
	 * Return the vector with positive coordinates.
	 */
	public Vector2D abs () {
		return new Vector2D(Math.abs(this.x), Math.abs(this.y));
	}
	
	/**
	 * Returns true if vector is null.
	 */
	public boolean isNullVector() {
		return this.x == 0f && this.y == 0f;
	}

	/**
     * return the vector in coordinates of the global frame
     */
	public Vector2D toGlobal (Vector2D frame) {
		
		return this.vadd(frame);
	}
	

	/**
     * Returns the vector in coordinates of another frame
     */
	public Vector2D toLocal (Vector2D frame) {
		
		return this.vsub(frame);
	}

	/* Opérations élémentaires */

	/**
     * Multiplication by a scalar */
	public Vector2D mul (float scalar) {
		
		return new Vector2D(this.x * scalar, this.y * scalar);
	}
	
	/** Retuen the reciprocal vector. */
	public Vector2D vreciprocal () {
		return new Vector2D(1f / this.x, 1f / this.y);
	}
	
	/**
     * Add two vectors
     */
	public Vector2D vadd (Vector2D other) {
		
		return new Vector2D(this.x + other.x, this.y + other.y);
	}

	/**
     * Substract one vector with the other
     */
	public Vector2D vsub (Vector2D other) {
		
		return new Vector2D(this.x - other.x, this.y - other.y);
	}

	/**
	 * Multiply corresponding coordinates together.
	 * 
	 * DISCLAIMER: This operation does not mathematically exists.
	 * It is just practical.
	 */
	public Vector2D vmul(Vector2D vec) {
		return new Vector2D(this.x * vec.x, this.y * vec.y);
	}
	
	/**
     * Return the magnitude of the vector
     */
	public float getLength () {
		
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}


	/**
     * Return the squared magnitude of the vector
     * 
     * Use case: magnitude comparison. 
     */
	public float getSqrdLength () {
		
		return this.x * this.x + this.y * this.y;
	}

	/**
     * Sets the magnitude of the vector and return it
     *
     * If length is 0, then it won't change
     */
	public Vector2D setLength (float newLen) {
		
		Vector2D newVec = this.normalized().mul(newLen);
		
		this.x = newVec.x;
		this.y = newVec.y;

		return this;
	}
	

	public float getAngle () {
		
		if (this.getSqrdLength() == 0)
			return 0;
		
		return Math.atan2(this.y, this.x);
	}
	
	public void setAngle (float theta) {
		
		this.x = this.getLength();
		this.y = 0;
		
		this.rotate(theta);
	}


	/**
     * Return angle between two vectors
     */
	public float angleTo (Vector2D other) {
		
		return Math.atan2(this.crossProduct(other), this.dotProduct(other));
	}
	
	/**
     * Rotate and modify the vector
     */
	public void rotate (float theta) {
		
		float cosTheta = Math.cos(theta);
		float sinTheta = Math.sin(theta);
		
		this.x = this.x * cosTheta - this.y * sinTheta;
		this.y = this.x * sinTheta + this.y * cosTheta;
	}

	/**
     * Return a new rotated vector
     */
	public Vector2D rotated (float theta) {
//		float len = this.getLength();	
//		return new Vector2D(len * Math.cos(theta), len * Math.sin(theta));
		
		float cosTheta = Math.cos(theta);
		float sinTheta = Math.sin(theta);
		
		return new Vector2D(this.x * cosTheta - this.y * sinTheta,this.x * sinTheta + this.y * cosTheta);
	}
	
	/* Operations usuelles sur les vecteurs. */

	/**
     * Return the dot product or scalar product
     */
	public float dotProduct (Vector2D other) {
		
		return (this.x * other.x + this.y * other.y);
	}


	/**
     * Adaptation of the cross product in two dimensions
     
     * Return the length of the vector resulting from cross product
     */
	public float crossProduct (Vector2D other) {
		
		return (this.x * other.y - this.y * other.x);
	}
	

	/**
     * Return the length of the projection of this vector on the other
     */
	public float scalarProjection (Vector2D other) {
		
		return this.dotProduct(other) / other.getSqrdLength();
	}
	
	/**
	 * Project this vector on the other
	 */
	public void project (Vector2D other) {
		
		Vector2D projection = this.mul(this.scalarProjection(other));
		
		this.x = projection.x;
		this.y = projection.y;
	}

	/** 
	 * Set length of this vector to 1
	 */
	public void normalize () {
		
		float len = this.getLength();
		
		if(len == 0) {
		    this.x = 0;
		    this.y = 0;
		}
		
		this.x = this.x / len;
		this.y = this.y / len;
	}
	

	/**
	 * Return the normalized vector
	 */
	public Vector2D normalized () {
		
		float len = this.getLength();
		
		if(len == 0) return new Vector2D();
		
		return new Vector2D(this.x / len, this.y / len);
	}


	/**
     * Return a vector resulting of a rotation of PI/2 radians, same magnitude
     */
	public Vector2D orthogonal () {
		
		return new Vector2D(-this.y, this.x);
	}


	/**
     * Reflect this vector with respect to a normal
     */
	public Vector2D reflect (Vector2D normal) {
		
		float dot = this.dotProduct(normal);
		
		return new Vector2D(this.x - 2f * normal.x * dot, this.y - 2f * normal.y * dot);
	}
	
	/**
	 * Return a copy of this vector
	 */
	public Vector2D copy () {
		return new Vector2D(this.x, this.y);
	}
	
	@Override
	public int compareTo (Vector2D other) {
		if (other.x < this.x) {
			if (other.y < this.y) return 4;
			if (other.y > this.y) return 2;
			else return 3;
		} else if (other.x > this.x) {
			if (other.y < this.y) return -4;
			if (other.y > this.y) return -2;
			else return -3;
		} else {
			if (other.y > this.y) return -1;
			if (other.y < this.y) return 1;
			else return 0;
		}
	}
	
	@Override
	public String toString () {
		return "(" + Math.round(this.x*1000f)/1000f + "," +
			Math.round(this.y*1000f)/1000f + ")";
//		return "(" + Math.round(this.x) + "," + Math.round(this.y) + ")";
	}
}
