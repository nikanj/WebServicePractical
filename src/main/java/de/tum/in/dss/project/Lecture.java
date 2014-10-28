/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package de.tum.in.dss.project;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lecture implements org.apache.thrift.TBase<Lecture, Lecture._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Lecture");

  private static final org.apache.thrift.protocol.TField LECTURE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("lectureId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField START_AT_FIELD_DESC = new org.apache.thrift.protocol.TField("startAt", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField END_AT_FIELD_DESC = new org.apache.thrift.protocol.TField("endAt", org.apache.thrift.protocol.TType.I64, (short)4);
  private static final org.apache.thrift.protocol.TField RATEABLE_AFTER_FIELD_DESC = new org.apache.thrift.protocol.TField("rateableAfter", org.apache.thrift.protocol.TType.I64, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new LectureStandardSchemeFactory());
    schemes.put(TupleScheme.class, new LectureTupleSchemeFactory());
  }

  public int lectureId; // required
  public String name; // required
  public long startAt; // optional
  public long endAt; // optional
  public long rateableAfter; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    LECTURE_ID((short)1, "lectureId"),
    NAME((short)2, "name"),
    START_AT((short)3, "startAt"),
    END_AT((short)4, "endAt"),
    RATEABLE_AFTER((short)5, "rateableAfter");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // LECTURE_ID
          return LECTURE_ID;
        case 2: // NAME
          return NAME;
        case 3: // START_AT
          return START_AT;
        case 4: // END_AT
          return END_AT;
        case 5: // RATEABLE_AFTER
          return RATEABLE_AFTER;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __LECTUREID_ISSET_ID = 0;
  private static final int __STARTAT_ISSET_ID = 1;
  private static final int __ENDAT_ISSET_ID = 2;
  private static final int __RATEABLEAFTER_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.START_AT,_Fields.END_AT,_Fields.RATEABLE_AFTER};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.LECTURE_ID, new org.apache.thrift.meta_data.FieldMetaData("lectureId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.START_AT, new org.apache.thrift.meta_data.FieldMetaData("startAt", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.END_AT, new org.apache.thrift.meta_data.FieldMetaData("endAt", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.RATEABLE_AFTER, new org.apache.thrift.meta_data.FieldMetaData("rateableAfter", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Lecture.class, metaDataMap);
  }

  public Lecture() {
  }

  public Lecture(
    int lectureId,
    String name)
  {
    this();
    this.lectureId = lectureId;
    setLectureIdIsSet(true);
    this.name = name;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Lecture(Lecture other) {
    __isset_bitfield = other.__isset_bitfield;
    this.lectureId = other.lectureId;
    if (other.isSetName()) {
      this.name = other.name;
    }
    this.startAt = other.startAt;
    this.endAt = other.endAt;
    this.rateableAfter = other.rateableAfter;
  }

  public Lecture deepCopy() {
    return new Lecture(this);
  }

  @Override
  public void clear() {
    setLectureIdIsSet(false);
    this.lectureId = 0;
    this.name = null;
    setStartAtIsSet(false);
    this.startAt = 0;
    setEndAtIsSet(false);
    this.endAt = 0;
    setRateableAfterIsSet(false);
    this.rateableAfter = 0;
  }

  public int getLectureId() {
    return this.lectureId;
  }

  public Lecture setLectureId(int lectureId) {
    this.lectureId = lectureId;
    setLectureIdIsSet(true);
    return this;
  }

  public void unsetLectureId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LECTUREID_ISSET_ID);
  }

  /** Returns true if field lectureId is set (has been assigned a value) and false otherwise */
  public boolean isSetLectureId() {
    return EncodingUtils.testBit(__isset_bitfield, __LECTUREID_ISSET_ID);
  }

  public void setLectureIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LECTUREID_ISSET_ID, value);
  }

  public String getName() {
    return this.name;
  }

  public Lecture setName(String name) {
    this.name = name;
    return this;
  }

  public void unsetName() {
    this.name = null;
  }

  /** Returns true if field name is set (has been assigned a value) and false otherwise */
  public boolean isSetName() {
    return this.name != null;
  }

  public void setNameIsSet(boolean value) {
    if (!value) {
      this.name = null;
    }
  }

  public long getStartAt() {
    return this.startAt;
  }

  public Lecture setStartAt(long startAt) {
    this.startAt = startAt;
    setStartAtIsSet(true);
    return this;
  }

  public void unsetStartAt() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __STARTAT_ISSET_ID);
  }

  /** Returns true if field startAt is set (has been assigned a value) and false otherwise */
  public boolean isSetStartAt() {
    return EncodingUtils.testBit(__isset_bitfield, __STARTAT_ISSET_ID);
  }

  public void setStartAtIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __STARTAT_ISSET_ID, value);
  }

  public long getEndAt() {
    return this.endAt;
  }

  public Lecture setEndAt(long endAt) {
    this.endAt = endAt;
    setEndAtIsSet(true);
    return this;
  }

  public void unsetEndAt() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ENDAT_ISSET_ID);
  }

  /** Returns true if field endAt is set (has been assigned a value) and false otherwise */
  public boolean isSetEndAt() {
    return EncodingUtils.testBit(__isset_bitfield, __ENDAT_ISSET_ID);
  }

  public void setEndAtIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ENDAT_ISSET_ID, value);
  }

  public long getRateableAfter() {
    return this.rateableAfter;
  }

  public Lecture setRateableAfter(long rateableAfter) {
    this.rateableAfter = rateableAfter;
    setRateableAfterIsSet(true);
    return this;
  }

  public void unsetRateableAfter() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __RATEABLEAFTER_ISSET_ID);
  }

  /** Returns true if field rateableAfter is set (has been assigned a value) and false otherwise */
  public boolean isSetRateableAfter() {
    return EncodingUtils.testBit(__isset_bitfield, __RATEABLEAFTER_ISSET_ID);
  }

  public void setRateableAfterIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __RATEABLEAFTER_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case LECTURE_ID:
      if (value == null) {
        unsetLectureId();
      } else {
        setLectureId((Integer)value);
      }
      break;

    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((String)value);
      }
      break;

    case START_AT:
      if (value == null) {
        unsetStartAt();
      } else {
        setStartAt((Long)value);
      }
      break;

    case END_AT:
      if (value == null) {
        unsetEndAt();
      } else {
        setEndAt((Long)value);
      }
      break;

    case RATEABLE_AFTER:
      if (value == null) {
        unsetRateableAfter();
      } else {
        setRateableAfter((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case LECTURE_ID:
      return Integer.valueOf(getLectureId());

    case NAME:
      return getName();

    case START_AT:
      return Long.valueOf(getStartAt());

    case END_AT:
      return Long.valueOf(getEndAt());

    case RATEABLE_AFTER:
      return Long.valueOf(getRateableAfter());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case LECTURE_ID:
      return isSetLectureId();
    case NAME:
      return isSetName();
    case START_AT:
      return isSetStartAt();
    case END_AT:
      return isSetEndAt();
    case RATEABLE_AFTER:
      return isSetRateableAfter();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Lecture)
      return this.equals((Lecture)that);
    return false;
  }

  public boolean equals(Lecture that) {
    if (that == null)
      return false;

    boolean this_present_lectureId = true;
    boolean that_present_lectureId = true;
    if (this_present_lectureId || that_present_lectureId) {
      if (!(this_present_lectureId && that_present_lectureId))
        return false;
      if (this.lectureId != that.lectureId)
        return false;
    }

    boolean this_present_name = true && this.isSetName();
    boolean that_present_name = true && that.isSetName();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
        return false;
    }

    boolean this_present_startAt = true && this.isSetStartAt();
    boolean that_present_startAt = true && that.isSetStartAt();
    if (this_present_startAt || that_present_startAt) {
      if (!(this_present_startAt && that_present_startAt))
        return false;
      if (this.startAt != that.startAt)
        return false;
    }

    boolean this_present_endAt = true && this.isSetEndAt();
    boolean that_present_endAt = true && that.isSetEndAt();
    if (this_present_endAt || that_present_endAt) {
      if (!(this_present_endAt && that_present_endAt))
        return false;
      if (this.endAt != that.endAt)
        return false;
    }

    boolean this_present_rateableAfter = true && this.isSetRateableAfter();
    boolean that_present_rateableAfter = true && that.isSetRateableAfter();
    if (this_present_rateableAfter || that_present_rateableAfter) {
      if (!(this_present_rateableAfter && that_present_rateableAfter))
        return false;
      if (this.rateableAfter != that.rateableAfter)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(Lecture other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    Lecture typedOther = (Lecture)other;

    lastComparison = Boolean.valueOf(isSetLectureId()).compareTo(typedOther.isSetLectureId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLectureId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lectureId, typedOther.lectureId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetName()).compareTo(typedOther.isSetName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.name, typedOther.name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStartAt()).compareTo(typedOther.isSetStartAt());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStartAt()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.startAt, typedOther.startAt);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEndAt()).compareTo(typedOther.isSetEndAt());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEndAt()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.endAt, typedOther.endAt);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRateableAfter()).compareTo(typedOther.isSetRateableAfter());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRateableAfter()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.rateableAfter, typedOther.rateableAfter);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Lecture(");
    boolean first = true;

    sb.append("lectureId:");
    sb.append(this.lectureId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("name:");
    if (this.name == null) {
      sb.append("null");
    } else {
      sb.append(this.name);
    }
    first = false;
    if (isSetStartAt()) {
      if (!first) sb.append(", ");
      sb.append("startAt:");
      sb.append(this.startAt);
      first = false;
    }
    if (isSetEndAt()) {
      if (!first) sb.append(", ");
      sb.append("endAt:");
      sb.append(this.endAt);
      first = false;
    }
    if (isSetRateableAfter()) {
      if (!first) sb.append(", ");
      sb.append("rateableAfter:");
      sb.append(this.rateableAfter);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class LectureStandardSchemeFactory implements SchemeFactory {
    public LectureStandardScheme getScheme() {
      return new LectureStandardScheme();
    }
  }

  private static class LectureStandardScheme extends StandardScheme<Lecture> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Lecture struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // LECTURE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.lectureId = iprot.readI32();
              struct.setLectureIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.name = iprot.readString();
              struct.setNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // START_AT
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.startAt = iprot.readI64();
              struct.setStartAtIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // END_AT
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.endAt = iprot.readI64();
              struct.setEndAtIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // RATEABLE_AFTER
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.rateableAfter = iprot.readI64();
              struct.setRateableAfterIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Lecture struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(LECTURE_ID_FIELD_DESC);
      oprot.writeI32(struct.lectureId);
      oprot.writeFieldEnd();
      if (struct.name != null) {
        oprot.writeFieldBegin(NAME_FIELD_DESC);
        oprot.writeString(struct.name);
        oprot.writeFieldEnd();
      }
      if (struct.isSetStartAt()) {
        oprot.writeFieldBegin(START_AT_FIELD_DESC);
        oprot.writeI64(struct.startAt);
        oprot.writeFieldEnd();
      }
      if (struct.isSetEndAt()) {
        oprot.writeFieldBegin(END_AT_FIELD_DESC);
        oprot.writeI64(struct.endAt);
        oprot.writeFieldEnd();
      }
      if (struct.isSetRateableAfter()) {
        oprot.writeFieldBegin(RATEABLE_AFTER_FIELD_DESC);
        oprot.writeI64(struct.rateableAfter);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class LectureTupleSchemeFactory implements SchemeFactory {
    public LectureTupleScheme getScheme() {
      return new LectureTupleScheme();
    }
  }

  private static class LectureTupleScheme extends TupleScheme<Lecture> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Lecture struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetLectureId()) {
        optionals.set(0);
      }
      if (struct.isSetName()) {
        optionals.set(1);
      }
      if (struct.isSetStartAt()) {
        optionals.set(2);
      }
      if (struct.isSetEndAt()) {
        optionals.set(3);
      }
      if (struct.isSetRateableAfter()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetLectureId()) {
        oprot.writeI32(struct.lectureId);
      }
      if (struct.isSetName()) {
        oprot.writeString(struct.name);
      }
      if (struct.isSetStartAt()) {
        oprot.writeI64(struct.startAt);
      }
      if (struct.isSetEndAt()) {
        oprot.writeI64(struct.endAt);
      }
      if (struct.isSetRateableAfter()) {
        oprot.writeI64(struct.rateableAfter);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Lecture struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.lectureId = iprot.readI32();
        struct.setLectureIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.name = iprot.readString();
        struct.setNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.startAt = iprot.readI64();
        struct.setStartAtIsSet(true);
      }
      if (incoming.get(3)) {
        struct.endAt = iprot.readI64();
        struct.setEndAtIsSet(true);
      }
      if (incoming.get(4)) {
        struct.rateableAfter = iprot.readI64();
        struct.setRateableAfterIsSet(true);
      }
    }
  }

}

