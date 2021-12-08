package com.redhat.dto;

import java.io.Serializable;

public class CustomerSuccess implements Serializable {
  public CustomerSuccess(){
    this.account = new Account();
    this.progression = new Progression();
  }
  public CustomerSuccess(Customer cus){
    this();
    this.id = 0;
    this.name = cus.name;
    this.address = cus.address;
    this.isBackOrder = Boolean.valueOf(cus.isBackOrder);
    this.isPartial = Boolean.valueOf(cus.isPartial);
    this.isPickingBox = Boolean.valueOf(cus.isPickingBox);
    this.shippingType = cus.shippingType.name;
    this.inventoryRotation = cus.inventoryRotation.name;
    this.isActive = false;
    this.warehouseId = 0;
  }
  public int id;
  public String name;
  public Account account;
  public Object address;
  public boolean isBackOrder;
  public boolean isPartial;
  public boolean isPickingBox;
  public String shippingType;
  public String inventoryRotation;
  public Progression progression;
  public int minimumShelfLife;
  public int reservationPriority;
  public boolean isActive;
  public int warehouseId;
}