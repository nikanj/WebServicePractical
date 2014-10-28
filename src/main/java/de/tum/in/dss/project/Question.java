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

public class Question implements org.apache.thrift.TBase<Question, Question._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Question");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField LECTURE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("lectureId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("time", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField QUESTION_TEXT_FIELD_DESC = new org.apache.thrift.protocol.TField("questionText", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField VOTING_FIELD_DESC = new org.apache.thrift.protocol.TField("voting", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField ANSWERS_FIELD_DESC = new org.apache.thrift.protocol.TField("answers", org.apache.thrift.protocol.TType.LIST, (short)6);
  private static final org.apache.thrift.protocol.TField IS_ANSWERED_FIELD_DESC = new org.apache.thrift.protocol.TField("isAnswered", org.apache.thrift.protocol.TType.BOOL, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new QuestionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new QuestionTupleSchemeFactory());
  }

  public int id; // required
  public int lectureId; // required
  public long time; // required
  public String questionText; // required
  public int voting; // required
  public List<String> answers; // optional
  public boolean isAnswered; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    LECTURE_ID((short)2, "lectureId"),
    TIME((short)3, "time"),
    QUESTION_TEXT((short)4, "questionText"),
    VOTING((short)5, "voting"),
    ANSWERS((short)6, "answers"),
    IS_ANSWERED((short)7, "isAnswered");

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
        case 1: // ID
          return ID;
        case 2: // LECTURE_ID
          return LECTURE_ID;
        case 3: // TIME
          return TIME;
        case 4: // QUESTION_TEXT
          return QUESTION_TEXT;
        case 5: // VOTING
          return VOTING;
        case 6: // ANSWERS
          return ANSWERS;
        case 7: // IS_ANSWERED
          return IS_ANSWERED;
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
  private static final int __ID_ISSET_ID = 0;
  private static final int __LECTUREID_ISSET_ID = 1;
  private static final int __TIME_ISSET_ID = 2;
  private static final int __VOTING_ISSET_ID = 3;
  private static final int __ISANSWERED_ISSET_ID = 4;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.ANSWERS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LECTURE_ID, new org.apache.thrift.meta_data.FieldMetaData("lectureId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.TIME, new org.apache.thrift.meta_data.FieldMetaData("time", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.QUESTION_TEXT, new org.apache.thrift.meta_data.FieldMetaData("questionText", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.VOTING, new org.apache.thrift.meta_data.FieldMetaData("voting", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ANSWERS, new org.apache.thrift.meta_data.FieldMetaData("answers", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.IS_ANSWERED, new org.apache.thrift.meta_data.FieldMetaData("isAnswered", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Question.class, metaDataMap);
  }

  public Question() {
    this.voting = 0;

    this.isAnswered = false;

  }

  public Question(
    int id,
    int lectureId,
    long time,
    String questionText,
    int voting,
    boolean isAnswered)
  {
    this();
    this.id = id;
    setIdIsSet(true);
    this.lectureId = lectureId;
    setLectureIdIsSet(true);
    this.time = time;
    setTimeIsSet(true);
    this.questionText = questionText;
    this.voting = voting;
    setVotingIsSet(true);
    this.isAnswered = isAnswered;
    setIsAnsweredIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Question(Question other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    this.lectureId = other.lectureId;
    this.time = other.time;
    if (other.isSetQuestionText()) {
      this.questionText = other.questionText;
    }
    this.voting = other.voting;
    if (other.isSetAnswers()) {
      List<String> __this__answers = new ArrayList<String>();
      for (String other_element : other.answers) {
        __this__answers.add(other_element);
      }
      this.answers = __this__answers;
    }
    this.isAnswered = other.isAnswered;
  }

  public Question deepCopy() {
    return new Question(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    setLectureIdIsSet(false);
    this.lectureId = 0;
    setTimeIsSet(false);
    this.time = 0;
    this.questionText = null;
    this.voting = 0;

    this.answers = null;
    this.isAnswered = false;

  }

  public int getId() {
    return this.id;
  }

  public Question setId(int id) {
    this.id = id;
    setIdIsSet(true);
    return this;
  }

  public void unsetId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ID_ISSET_ID);
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return EncodingUtils.testBit(__isset_bitfield, __ID_ISSET_ID);
  }

  public void setIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ID_ISSET_ID, value);
  }

  public int getLectureId() {
    return this.lectureId;
  }

  public Question setLectureId(int lectureId) {
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

  public long getTime() {
    return this.time;
  }

  public Question setTime(long time) {
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

  public String getQuestionText() {
    return this.questionText;
  }

  public Question setQuestionText(String questionText) {
    this.questionText = questionText;
    return this;
  }

  public void unsetQuestionText() {
    this.questionText = null;
  }

  /** Returns true if field questionText is set (has been assigned a value) and false otherwise */
  public boolean isSetQuestionText() {
    return this.questionText != null;
  }

  public void setQuestionTextIsSet(boolean value) {
    if (!value) {
      this.questionText = null;
    }
  }

  public int getVoting() {
    return this.voting;
  }

  public Question setVoting(int voting) {
    this.voting = voting;
    setVotingIsSet(true);
    return this;
  }

  public void unsetVoting() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __VOTING_ISSET_ID);
  }

  /** Returns true if field voting is set (has been assigned a value) and false otherwise */
  public boolean isSetVoting() {
    return EncodingUtils.testBit(__isset_bitfield, __VOTING_ISSET_ID);
  }

  public void setVotingIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __VOTING_ISSET_ID, value);
  }

  public int getAnswersSize() {
    return (this.answers == null) ? 0 : this.answers.size();
  }

  public java.util.Iterator<String> getAnswersIterator() {
    return (this.answers == null) ? null : this.answers.iterator();
  }

  public void addToAnswers(String elem) {
    if (this.answers == null) {
      this.answers = new ArrayList<String>();
    }
    this.answers.add(elem);
  }

  public List<String> getAnswers() {
    return this.answers;
  }

  public Question setAnswers(List<String> answers) {
    this.answers = answers;
    return this;
  }

  public void unsetAnswers() {
    this.answers = null;
  }

  /** Returns true if field answers is set (has been assigned a value) and false otherwise */
  public boolean isSetAnswers() {
    return this.answers != null;
  }

  public void setAnswersIsSet(boolean value) {
    if (!value) {
      this.answers = null;
    }
  }

  public boolean isIsAnswered() {
    return this.isAnswered;
  }

  public Question setIsAnswered(boolean isAnswered) {
    this.isAnswered = isAnswered;
    setIsAnsweredIsSet(true);
    return this;
  }

  public void unsetIsAnswered() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ISANSWERED_ISSET_ID);
  }

  /** Returns true if field isAnswered is set (has been assigned a value) and false otherwise */
  public boolean isSetIsAnswered() {
    return EncodingUtils.testBit(__isset_bitfield, __ISANSWERED_ISSET_ID);
  }

  public void setIsAnsweredIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ISANSWERED_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((Integer)value);
      }
      break;

    case LECTURE_ID:
      if (value == null) {
        unsetLectureId();
      } else {
        setLectureId((Integer)value);
      }
      break;

    case TIME:
      if (value == null) {
        unsetTime();
      } else {
        setTime((Long)value);
      }
      break;

    case QUESTION_TEXT:
      if (value == null) {
        unsetQuestionText();
      } else {
        setQuestionText((String)value);
      }
      break;

    case VOTING:
      if (value == null) {
        unsetVoting();
      } else {
        setVoting((Integer)value);
      }
      break;

    case ANSWERS:
      if (value == null) {
        unsetAnswers();
      } else {
        setAnswers((List<String>)value);
      }
      break;

    case IS_ANSWERED:
      if (value == null) {
        unsetIsAnswered();
      } else {
        setIsAnswered((Boolean)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return Integer.valueOf(getId());

    case LECTURE_ID:
      return Integer.valueOf(getLectureId());

    case TIME:
      return Long.valueOf(getTime());

    case QUESTION_TEXT:
      return getQuestionText();

    case VOTING:
      return Integer.valueOf(getVoting());

    case ANSWERS:
      return getAnswers();

    case IS_ANSWERED:
      return Boolean.valueOf(isIsAnswered());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case LECTURE_ID:
      return isSetLectureId();
    case TIME:
      return isSetTime();
    case QUESTION_TEXT:
      return isSetQuestionText();
    case VOTING:
      return isSetVoting();
    case ANSWERS:
      return isSetAnswers();
    case IS_ANSWERED:
      return isSetIsAnswered();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Question)
      return this.equals((Question)that);
    return false;
  }

  public boolean equals(Question that) {
    if (that == null)
      return false;

    boolean this_present_id = true;
    boolean that_present_id = true;
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    boolean this_present_lectureId = true;
    boolean that_present_lectureId = true;
    if (this_present_lectureId || that_present_lectureId) {
      if (!(this_present_lectureId && that_present_lectureId))
        return false;
      if (this.lectureId != that.lectureId)
        return false;
    }

    boolean this_present_time = true;
    boolean that_present_time = true;
    if (this_present_time || that_present_time) {
      if (!(this_present_time && that_present_time))
        return false;
      if (this.time != that.time)
        return false;
    }

    boolean this_present_questionText = true && this.isSetQuestionText();
    boolean that_present_questionText = true && that.isSetQuestionText();
    if (this_present_questionText || that_present_questionText) {
      if (!(this_present_questionText && that_present_questionText))
        return false;
      if (!this.questionText.equals(that.questionText))
        return false;
    }

    boolean this_present_voting = true;
    boolean that_present_voting = true;
    if (this_present_voting || that_present_voting) {
      if (!(this_present_voting && that_present_voting))
        return false;
      if (this.voting != that.voting)
        return false;
    }

    boolean this_present_answers = true && this.isSetAnswers();
    boolean that_present_answers = true && that.isSetAnswers();
    if (this_present_answers || that_present_answers) {
      if (!(this_present_answers && that_present_answers))
        return false;
      if (!this.answers.equals(that.answers))
        return false;
    }

    boolean this_present_isAnswered = true;
    boolean that_present_isAnswered = true;
    if (this_present_isAnswered || that_present_isAnswered) {
      if (!(this_present_isAnswered && that_present_isAnswered))
        return false;
      if (this.isAnswered != that.isAnswered)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(Question other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    Question typedOther = (Question)other;

    lastComparison = Boolean.valueOf(isSetId()).compareTo(typedOther.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, typedOther.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
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
    lastComparison = Boolean.valueOf(isSetQuestionText()).compareTo(typedOther.isSetQuestionText());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetQuestionText()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.questionText, typedOther.questionText);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetVoting()).compareTo(typedOther.isSetVoting());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVoting()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.voting, typedOther.voting);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAnswers()).compareTo(typedOther.isSetAnswers());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAnswers()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.answers, typedOther.answers);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIsAnswered()).compareTo(typedOther.isSetIsAnswered());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIsAnswered()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.isAnswered, typedOther.isAnswered);
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
    StringBuilder sb = new StringBuilder("Question(");
    boolean first = true;

    sb.append("id:");
    sb.append(this.id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("lectureId:");
    sb.append(this.lectureId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("time:");
    sb.append(this.time);
    first = false;
    if (!first) sb.append(", ");
    sb.append("questionText:");
    if (this.questionText == null) {
      sb.append("null");
    } else {
      sb.append(this.questionText);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("voting:");
    sb.append(this.voting);
    first = false;
    if (isSetAnswers()) {
      if (!first) sb.append(", ");
      sb.append("answers:");
      if (this.answers == null) {
        sb.append("null");
      } else {
        sb.append(this.answers);
      }
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("isAnswered:");
    sb.append(this.isAnswered);
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

  private static class QuestionStandardSchemeFactory implements SchemeFactory {
    public QuestionStandardScheme getScheme() {
      return new QuestionStandardScheme();
    }
  }

  private static class QuestionStandardScheme extends StandardScheme<Question> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Question struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.id = iprot.readI32();
              struct.setIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // LECTURE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.lectureId = iprot.readI32();
              struct.setLectureIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.time = iprot.readI64();
              struct.setTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // QUESTION_TEXT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.questionText = iprot.readString();
              struct.setQuestionTextIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // VOTING
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.voting = iprot.readI32();
              struct.setVotingIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // ANSWERS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.answers = new ArrayList<String>(_list0.size);
                for (int _i1 = 0; _i1 < _list0.size; ++_i1)
                {
                  String _elem2; // required
                  _elem2 = iprot.readString();
                  struct.answers.add(_elem2);
                }
                iprot.readListEnd();
              }
              struct.setAnswersIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // IS_ANSWERED
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.isAnswered = iprot.readBool();
              struct.setIsAnsweredIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Question struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ID_FIELD_DESC);
      oprot.writeI32(struct.id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(LECTURE_ID_FIELD_DESC);
      oprot.writeI32(struct.lectureId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(TIME_FIELD_DESC);
      oprot.writeI64(struct.time);
      oprot.writeFieldEnd();
      if (struct.questionText != null) {
        oprot.writeFieldBegin(QUESTION_TEXT_FIELD_DESC);
        oprot.writeString(struct.questionText);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(VOTING_FIELD_DESC);
      oprot.writeI32(struct.voting);
      oprot.writeFieldEnd();
      if (struct.answers != null) {
        if (struct.isSetAnswers()) {
          oprot.writeFieldBegin(ANSWERS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.answers.size()));
            for (String _iter3 : struct.answers)
            {
              oprot.writeString(_iter3);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldBegin(IS_ANSWERED_FIELD_DESC);
      oprot.writeBool(struct.isAnswered);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class QuestionTupleSchemeFactory implements SchemeFactory {
    public QuestionTupleScheme getScheme() {
      return new QuestionTupleScheme();
    }
  }

  private static class QuestionTupleScheme extends TupleScheme<Question> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Question struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetLectureId()) {
        optionals.set(1);
      }
      if (struct.isSetTime()) {
        optionals.set(2);
      }
      if (struct.isSetQuestionText()) {
        optionals.set(3);
      }
      if (struct.isSetVoting()) {
        optionals.set(4);
      }
      if (struct.isSetAnswers()) {
        optionals.set(5);
      }
      if (struct.isSetIsAnswered()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetLectureId()) {
        oprot.writeI32(struct.lectureId);
      }
      if (struct.isSetTime()) {
        oprot.writeI64(struct.time);
      }
      if (struct.isSetQuestionText()) {
        oprot.writeString(struct.questionText);
      }
      if (struct.isSetVoting()) {
        oprot.writeI32(struct.voting);
      }
      if (struct.isSetAnswers()) {
        {
          oprot.writeI32(struct.answers.size());
          for (String _iter4 : struct.answers)
          {
            oprot.writeString(_iter4);
          }
        }
      }
      if (struct.isSetIsAnswered()) {
        oprot.writeBool(struct.isAnswered);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Question struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.lectureId = iprot.readI32();
        struct.setLectureIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.time = iprot.readI64();
        struct.setTimeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.questionText = iprot.readString();
        struct.setQuestionTextIsSet(true);
      }
      if (incoming.get(4)) {
        struct.voting = iprot.readI32();
        struct.setVotingIsSet(true);
      }
      if (incoming.get(5)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.answers = new ArrayList<String>(_list5.size);
          for (int _i6 = 0; _i6 < _list5.size; ++_i6)
          {
            String _elem7; // required
            _elem7 = iprot.readString();
            struct.answers.add(_elem7);
          }
        }
        struct.setAnswersIsSet(true);
      }
      if (incoming.get(6)) {
        struct.isAnswered = iprot.readBool();
        struct.setIsAnsweredIsSet(true);
      }
    }
  }

}
