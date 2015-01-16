package org.cubixmc.util;


/**
*
* @author Jim Bergens
*/


public class Vector3I {

//TODO: Vector2D 


private Vector3I v3i;
// private Vector2D v2d;
private Vector3D v3d;


   @Override
    public String toString() {
        return "Vector3I{" + v3i + "}";
    }
    
    @Override
    public String toVector3D() {
    return "Vector3D{ + v3d + "}";
    }
    
    
    //========================[WIP]======================
    // @Override
    // public String toVector2D() {
    // return "Vector2D{ + v2d + "}";
    // }

}