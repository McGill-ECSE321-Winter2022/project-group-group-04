/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;

import javax.persistence.Entity;

// line 19 "../../../../../../model.ump"
// line 144 "../../../../../../model.ump"
@Entity
public class StoreOwner extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public StoreOwner(String aEmail, String aName, String aPassword)
  {
    super(aEmail, aName, aPassword);
  }

  public StoreOwner() {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }
}