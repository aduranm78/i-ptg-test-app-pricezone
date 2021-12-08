package com.redhat.dto;
import java.io.Serializable;

public class Customer implements Serializable {
  public String reservationPriority;
  public String isBackOrder;
  public String isPartial;
  public String isPickingBox;
  public String minimumShelfLife;
  public String name;
  public Descriptor account;
  public Descriptor inventoryRotation;
  public Descriptor progression;
  public Descriptor shippingType;
  public Warehouse warehouse;
  public Address address;
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("\t\t reservationPriority: " + reservationPriority + "\n");
    sb.append("\t\t isBackOrder: " + isBackOrder + "\n");
    sb.append("\t\t isPartial: " + isPartial + "\n");
    sb.append("\t\t isPickingBox: " + isPickingBox + "\n");
    sb.append("\t\t minimumShelfLife: " + minimumShelfLife + "\n");
    sb.append("\t\t name: " + name + "\n");
    sb.append("\t\t account: " + account.name + "\n");
    sb.append("\t\t inventoryRotation: " + inventoryRotation.name + "\n");
    sb.append("\t\t progression: " + progression.name + "\n");
    sb.append("\t\t shippingType: " + shippingType.name + "\n");
    sb.append("\t\t warehouse: " + warehouse.code + "\n");
    sb.append("\t\t address: " + address.nameAddress + "\n");
    return sb.toString();
  }
}