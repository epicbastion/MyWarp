/**
 * This class is generated by jOOQ
 */
package me.taylorkelly.mywarp.dataconnections.generated.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(
    value = {"http://www.jooq.org", "jOOQ version:3.5.1"},
    comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({"all", "unchecked", "rawtypes"})
public class WarpRecord
    extends org.jooq.impl.UpdatableRecordImpl<me.taylorkelly.mywarp.dataconnections.generated.tables.records.WarpRecord>
    implements
    org.jooq.Record13<org.jooq.types.UInteger, java.lang.String, org.jooq.types.UInteger, java.lang.Double, java.lang
        .Double, java.lang.Double, java.lang.Float, java.lang.Float, org.jooq.types.UInteger, java.util.Date, me
        .taylorkelly.mywarp.warp.Warp.Type, org.jooq.types.UInteger, java.lang.String> {

  private static final long serialVersionUID = 767445541;

  /**
   * Create a detached WarpRecord
   */
  public WarpRecord() {
    super(me.taylorkelly.mywarp.dataconnections.generated.tables.Warp.WARP);
  }

  /**
   * Create a detached, initialised WarpRecord
   */
  public WarpRecord(org.jooq.types.UInteger warpId, java.lang.String name, org.jooq.types.UInteger playerId,
                    java.lang.Double x, java.lang.Double y, java.lang.Double z, java.lang.Float pitch,
                    java.lang.Float yaw, org.jooq.types.UInteger worldId, java.util.Date creationDate,
                    me.taylorkelly.mywarp.warp.Warp.Type type, org.jooq.types.UInteger visits,
                    java.lang.String welcomeMessage) {
    super(me.taylorkelly.mywarp.dataconnections.generated.tables.Warp.WARP);

    setValue(0, warpId);
    setValue(1, name);
    setValue(2, playerId);
    setValue(3, x);
    setValue(4, y);
    setValue(5, z);
    setValue(6, pitch);
    setValue(7, yaw);
    setValue(8, worldId);
    setValue(9, creationDate);
    setValue(10, type);
    setValue(11, visits);
    setValue(12, welcomeMessage);
  }

  /**
   * Getter for <code>mywarp.warp.warp_id</code>.
   */
  public org.jooq.types.UInteger getWarpId() {
    return (org.jooq.types.UInteger) getValue(0);
  }

  /**
   * Setter for <code>mywarp.warp.warp_id</code>.
   */
  public void setWarpId(org.jooq.types.UInteger value) {
    setValue(0, value);
  }

  /**
   * Getter for <code>mywarp.warp.name</code>.
   */
  public java.lang.String getName() {
    return (java.lang.String) getValue(1);
  }

  /**
   * Setter for <code>mywarp.warp.name</code>.
   */
  public void setName(java.lang.String value) {
    setValue(1, value);
  }

  /**
   * Getter for <code>mywarp.warp.player_id</code>.
   */
  public org.jooq.types.UInteger getPlayerId() {
    return (org.jooq.types.UInteger) getValue(2);
  }

  /**
   * Setter for <code>mywarp.warp.player_id</code>.
   */
  public void setPlayerId(org.jooq.types.UInteger value) {
    setValue(2, value);
  }

  /**
   * Getter for <code>mywarp.warp.x</code>.
   */
  public java.lang.Double getX() {
    return (java.lang.Double) getValue(3);
  }

  /**
   * Setter for <code>mywarp.warp.x</code>.
   */
  public void setX(java.lang.Double value) {
    setValue(3, value);
  }

  /**
   * Getter for <code>mywarp.warp.y</code>.
   */
  public java.lang.Double getY() {
    return (java.lang.Double) getValue(4);
  }

  /**
   * Setter for <code>mywarp.warp.y</code>.
   */
  public void setY(java.lang.Double value) {
    setValue(4, value);
  }

  /**
   * Getter for <code>mywarp.warp.z</code>.
   */
  public java.lang.Double getZ() {
    return (java.lang.Double) getValue(5);
  }

  /**
   * Setter for <code>mywarp.warp.z</code>.
   */
  public void setZ(java.lang.Double value) {
    setValue(5, value);
  }

  /**
   * Getter for <code>mywarp.warp.pitch</code>.
   */
  public java.lang.Float getPitch() {
    return (java.lang.Float) getValue(6);
  }

  /**
   * Setter for <code>mywarp.warp.pitch</code>.
   */
  public void setPitch(java.lang.Float value) {
    setValue(6, value);
  }

  /**
   * Getter for <code>mywarp.warp.yaw</code>.
   */
  public java.lang.Float getYaw() {
    return (java.lang.Float) getValue(7);
  }

  /**
   * Setter for <code>mywarp.warp.yaw</code>.
   */
  public void setYaw(java.lang.Float value) {
    setValue(7, value);
  }

  /**
   * Getter for <code>mywarp.warp.world_id</code>.
   */
  public org.jooq.types.UInteger getWorldId() {
    return (org.jooq.types.UInteger) getValue(8);
  }

  /**
   * Setter for <code>mywarp.warp.world_id</code>.
   */
  public void setWorldId(org.jooq.types.UInteger value) {
    setValue(8, value);
  }

  /**
   * Getter for <code>mywarp.warp.creation_date</code>.
   */
  public java.util.Date getCreationDate() {
    return (java.util.Date) getValue(9);
  }

  /**
   * Setter for <code>mywarp.warp.creation_date</code>.
   */
  public void setCreationDate(java.util.Date value) {
    setValue(9, value);
  }

  /**
   * Getter for <code>mywarp.warp.type</code>.
   */
  public me.taylorkelly.mywarp.warp.Warp.Type getType() {
    return (me.taylorkelly.mywarp.warp.Warp.Type) getValue(10);
  }

  /**
   * Setter for <code>mywarp.warp.type</code>.
   */
  public void setType(me.taylorkelly.mywarp.warp.Warp.Type value) {
    setValue(10, value);
  }

  /**
   * Getter for <code>mywarp.warp.visits</code>.
   */
  public org.jooq.types.UInteger getVisits() {
    return (org.jooq.types.UInteger) getValue(11);
  }

  /**
   * Setter for <code>mywarp.warp.visits</code>.
   */
  public void setVisits(org.jooq.types.UInteger value) {
    setValue(11, value);
  }

  // -------------------------------------------------------------------------
  // Primary key information
  // -------------------------------------------------------------------------

  /**
   * Getter for <code>mywarp.warp.welcome_message</code>.
   */
  public java.lang.String getWelcomeMessage() {
    return (java.lang.String) getValue(12);
  }

  // -------------------------------------------------------------------------
  // Record13 type implementation
  // -------------------------------------------------------------------------

  /**
   * Setter for <code>mywarp.warp.welcome_message</code>.
   */
  public void setWelcomeMessage(java.lang.String value) {
    setValue(12, value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public org.jooq.Record1<org.jooq.types.UInteger> key() {
    return (org.jooq.Record1) super.key();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public org.jooq.Row13<org.jooq.types.UInteger, java.lang.String, org.jooq.types.UInteger, java.lang.Double, java
      .lang.Double, java.lang.Double, java.lang.Float, java.lang.Float, org.jooq.types.UInteger, java.util.Date, me
      .taylorkelly.mywarp.warp.Warp.Type, org.jooq.types.UInteger, java.lang.String> fieldsRow() {
    return (org.jooq.Row13) super.fieldsRow();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public org.jooq.Row13<org.jooq.types.UInteger, java.lang.String, org.jooq.types.UInteger, java.lang.Double, java
      .lang.Double, java.lang.Double, java.lang.Float, java.lang.Float, org.jooq.types.UInteger, java.util.Date, me
      .taylorkelly.mywarp.warp.Warp.Type, org.jooq.types.UInteger, java.lang.String> valuesRow() {
    return (org.jooq.Row13) super.valuesRow();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public org.jooq.Field<org.jooq.types.UInteger> field1() {
    return me.taylorkelly.mywarp.dataconnections.generated.tables.Warp.WARP.WARP_ID;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public org.jooq.Field<java.lang.String> field2() {
    return me.taylorkelly.mywarp.dataconnections.generated.tables.Warp.WARP.NAME;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public org.jooq.Field<org.jooq.types.UInteger> field3() {
    return me.taylorkelly.mywarp.dataconnections.generated.tables.Warp.WARP.PLAYER_ID;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public org.jooq.Field<java.lang.Double> field4() {
    return me.taylorkelly.mywarp.dataconnections.generated.tables.Warp.WARP.X;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public org.jooq.Field<java.lang.Double> field5() {
    return me.taylorkelly.mywarp.dataconnections.generated.tables.Warp.WARP.Y;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public org.jooq.Field<java.lang.Double> field6() {
    return me.taylorkelly.mywarp.dataconnections.generated.tables.Warp.WARP.Z;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public org.jooq.Field<java.lang.Float> field7() {
    return me.taylorkelly.mywarp.dataconnections.generated.tables.Warp.WARP.PITCH;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public org.jooq.Field<java.lang.Float> field8() {
    return me.taylorkelly.mywarp.dataconnections.generated.tables.Warp.WARP.YAW;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public org.jooq.Field<org.jooq.types.UInteger> field9() {
    return me.taylorkelly.mywarp.dataconnections.generated.tables.Warp.WARP.WORLD_ID;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public org.jooq.Field<java.util.Date> field10() {
    return me.taylorkelly.mywarp.dataconnections.generated.tables.Warp.WARP.CREATION_DATE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public org.jooq.Field<me.taylorkelly.mywarp.warp.Warp.Type> field11() {
    return me.taylorkelly.mywarp.dataconnections.generated.tables.Warp.WARP.TYPE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public org.jooq.Field<org.jooq.types.UInteger> field12() {
    return me.taylorkelly.mywarp.dataconnections.generated.tables.Warp.WARP.VISITS;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public org.jooq.Field<java.lang.String> field13() {
    return me.taylorkelly.mywarp.dataconnections.generated.tables.Warp.WARP.WELCOME_MESSAGE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public org.jooq.types.UInteger value1() {
    return getWarpId();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public java.lang.String value2() {
    return getName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public org.jooq.types.UInteger value3() {
    return getPlayerId();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public java.lang.Double value4() {
    return getX();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public java.lang.Double value5() {
    return getY();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public java.lang.Double value6() {
    return getZ();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public java.lang.Float value7() {
    return getPitch();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public java.lang.Float value8() {
    return getYaw();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public org.jooq.types.UInteger value9() {
    return getWorldId();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public java.util.Date value10() {
    return getCreationDate();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public me.taylorkelly.mywarp.warp.Warp.Type value11() {
    return getType();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public org.jooq.types.UInteger value12() {
    return getVisits();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public java.lang.String value13() {
    return getWelcomeMessage();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WarpRecord value1(org.jooq.types.UInteger value) {
    setWarpId(value);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WarpRecord value2(java.lang.String value) {
    setName(value);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WarpRecord value3(org.jooq.types.UInteger value) {
    setPlayerId(value);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WarpRecord value4(java.lang.Double value) {
    setX(value);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WarpRecord value5(java.lang.Double value) {
    setY(value);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WarpRecord value6(java.lang.Double value) {
    setZ(value);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WarpRecord value7(java.lang.Float value) {
    setPitch(value);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WarpRecord value8(java.lang.Float value) {
    setYaw(value);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WarpRecord value9(org.jooq.types.UInteger value) {
    setWorldId(value);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WarpRecord value10(java.util.Date value) {
    setCreationDate(value);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WarpRecord value11(me.taylorkelly.mywarp.warp.Warp.Type value) {
    setType(value);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WarpRecord value12(org.jooq.types.UInteger value) {
    setVisits(value);
    return this;
  }

  // -------------------------------------------------------------------------
  // Constructors
  // -------------------------------------------------------------------------

  /**
   * {@inheritDoc}
   */
  @Override
  public WarpRecord value13(java.lang.String value) {
    setWelcomeMessage(value);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WarpRecord values(org.jooq.types.UInteger value1, java.lang.String value2, org.jooq.types.UInteger value3,
                           java.lang.Double value4, java.lang.Double value5, java.lang.Double value6,
                           java.lang.Float value7, java.lang.Float value8, org.jooq.types.UInteger value9,
                           java.util.Date value10, me.taylorkelly.mywarp.warp.Warp.Type value11,
                           org.jooq.types.UInteger value12, java.lang.String value13) {
    return this;
  }
}
