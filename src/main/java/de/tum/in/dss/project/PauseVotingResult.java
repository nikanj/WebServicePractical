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

public class PauseVotingResult implements org.apache.thrift.TBase<PauseVotingResult, PauseVotingResult._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PauseVotingResult");

  private static final org.apache.thrift.protocol.TField TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("time", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField ENTRIES_FIELD_DESC = new org.apache.thrift.protocol.TField("entries", org.apache.thrift.protocol.TType.MAP, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new PauseVotingResultStandardSchemeFactory());
    schemes.put(TupleScheme.class, new PauseVotingResultTupleSchemeFactory());
  }

  public long time; // required
  public Map<Pause,Integer> entries; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TIME((short)1, "time"),
    ENTRIES((short)2, "entries");

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
        case 1: // TIME
          return TIME;
        case 2: // ENTRIES
          return ENTRIES;
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
  private static final int __TIME_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TIME, new org.apache.thrift.meta_data.FieldMetaData("time", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.ENTRIES, new org.apache.thrift.meta_data.FieldMetaData("entries", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, Pause.class), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PauseVotingResult.class, metaDataMap);
  }

  public PauseVotingResult() {
  }

  public PauseVotingResult(
    long time,
    Map<Pause,Integer> entries)
  {
    this();
    this.time = time;
    setTimeIsSet(true);
    this.entries = entries;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PauseVotingResult(PauseVotingResult other) {
    __isset_bitfield = other.__isset_bitfield;
    this.time = other.time;
    if (other.isSetEntries()) {
      Map<Pause,Integer> __this__entries = new HashMap<Pause,Integer>();
      for (Map.Entry<Pause, Integer> other_element : other.entries.entrySet()) {

        Pause other_element_key = other_element.getKey();
        Integer other_element_value = other_element.getValue();

        Pause __this__entries_copy_key = other_element_key;

        Integer __this__entries_copy_value = other_element_value;

        __this__entries.put(__this__entries_copy_key, __this__entries_copy_value);
      }
      this.entries = __this__entries;
    }
  }

  public PauseVotingResult deepCopy() {
    return new PauseVotingResult(this);
  }

  @Override
  public void clear() {
    setTimeIsSet(false);
    this.time = 0;
    this.entries = null;
  }

  public long getTime() {
    return this.time;
  }

  public PauseVotingResult setTime(long time) {
    this.time = time;
    setTimeIsSet(true);
    return this;
  }

  public void unsetTime() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TIME_ISSET_ID);
  }

  /** Returns true if field time is set (has been assigned a value) and false otherwise */
  public boolean isSetTime() {
    return EncodingUtils.testBit(__isset_bitfield, __TIME_ISSET_ID);
  }

  public void setTimeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TIME_ISSET_ID, value);
  }

  public int getEntriesSize() {
    return (this.entries == null) ? 0 : this.entries.size();
  }

  public void putToEntries(Pause key, int val) {
    if (this.entries == null) {
      this.entries = new HashMap<Pause,Integer>();
    }
    this.entries.put(key, val);
  }

  public Map<Pause,Integer> getEntries() {
    return this.entries;
  }

  public PauseVotingResult setEntries(Map<Pause,Integer> entries) {
    this.entries = entries;
    return this;
  }

  public void unsetEntries() {
    this.entries = null;
  }

  /** Returns true if field entries is set (has been assigned a value) and false otherwise */
  public boolean isSetEntries() {
    return this.entries != null;
  }

  public void setEntriesIsSet(boolean value) {
    if (!value) {
      this.entries = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TIME:
      if (value == null) {
        unsetTime();
      } else {
        setTime((Long)value);
      }
      break;

    case ENTRIES:
      if (value == null) {
        unsetEntries();
      } else {
        setEntries((Map<Pause,Integer>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TIME:
      return Long.valueOf(getTime());

    case ENTRIES:
      return getEntries();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TIME:
      return isSetTime();
    case ENTRIES:
      return isSetEntries();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof PauseVotingResult)
      return this.equals((PauseVotingResult)that);
    return false;
  }

  public boolean equals(PauseVotingResult that) {
    if (that == null)
      return false;

    boolean this_present_time = true;
    boolean that_present_time = true;
    if (this_present_time || that_present_time) {
      if (!(this_present_time && that_present_time))
        return false;
      if (this.time != that.time)
        return false;
    }

    boolean this_present_entries = true && this.isSetEntries();
    boolean that_present_entries = true && that.isSetEntries();
    if (this_present_entries || that_present_entries) {
      if (!(this_present_entries && that_present_entries))
        return false;
      if (!this.entries.equals(that.entries))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(PauseVotingResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    PauseVotingResult typedOther = (PauseVotingResult)other;

    lastComparison = Boolean.valueOf(isSetTime()).compareTo(typedOther.isSetTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.time, typedOther.time);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEntries()).compareTo(typedOther.isSetEntries());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEntries()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.entries, typedOther.entries);
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
    StringBuilder sb = new StringBuilder("PauseVotingResult(");
    boolean first = true;

    sb.append("time:");
    sb.append(this.time);
    first = false;
    if (!first) sb.append(", ");
    sb.append("entries:");
    if (this.entries == null) {
      sb.append("null");
    } else {
      sb.append(this.entries);
    }
    first = false;
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

  private static class PauseVotingResultStandardSchemeFactory implements SchemeFactory {
    public PauseVotingResultStandardScheme getScheme() {
      return new PauseVotingResultStandardScheme();
    }
  }

  private static class PauseVotingResultStandardScheme extends StandardScheme<PauseVotingResult> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PauseVotingResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.time = iprot.readI64();
              struct.setTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ENTRIES
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map18 = iprot.readMapBegin();
                struct.entries = new HashMap<Pause,Integer>(2*_map18.size);
                for (int _i19 = 0; _i19 < _map18.size; ++_i19)
                {
                  Pause _key20; // required
                  int _val21; // required
                  _key20 = Pause.findByValue(iprot.readI32());
                  _val21 = iprot.readI32();
                  struct.entries.put(_key20, _val21);
                }
                iprot.readMapEnd();
              }
              struct.setEntriesIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, PauseVotingResult struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(TIME_FIELD_DESC);
      oprot.writeI64(struct.time);
      oprot.writeFieldEnd();
      if (struct.entries != null) {
        oprot.writeFieldBegin(ENTRIES_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I32, org.apache.thrift.protocol.TType.I32, struct.entries.size()));
          for (Map.Entry<Pause, Integer> _iter22 : struct.entries.entrySet())
          {
            oprot.writeI32(_iter22.getKey().getValue());
            oprot.writeI32(_iter22.getValue());
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PauseVotingResultTupleSchemeFactory implements SchemeFactory {
    public PauseVotingResultTupleScheme getScheme() {
      return new PauseVotingResultTupleScheme();
    }
  }

  private static class PauseVotingResultTupleScheme extends TupleScheme<PauseVotingResult> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PauseVotingResult struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTime()) {
        optionals.set(0);
      }
      if (struct.isSetEntries()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetTime()) {
        oprot.writeI64(struct.time);
      }
      if (struct.isSetEntries()) {
        {
          oprot.writeI32(struct.entries.size());
          for (Map.Entry<Pause, Integer> _iter23 : struct.entries.entrySet())
          {
            oprot.writeI32(_iter23.getKey().getValue());
            oprot.writeI32(_iter23.getValue());
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PauseVotingResult struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.time = iprot.readI64();
        struct.setTimeIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TMap _map24 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I32, org.apache.thrift.protocol.TType.I32, iprot.readI32());
          struct.entries = new HashMap<Pause,Integer>(2*_map24.size);
          for (int _i25 = 0; _i25 < _map24.size; ++_i25)
          {
            Pause _key26; // required
            int _val27; // required
            _key26 = Pause.findByValue(iprot.readI32());
            _val27 = iprot.readI32();
            struct.entries.put(_key26, _val27);
          }
        }
        struct.setEntriesIsSet(true);
      }
    }
  }

}

