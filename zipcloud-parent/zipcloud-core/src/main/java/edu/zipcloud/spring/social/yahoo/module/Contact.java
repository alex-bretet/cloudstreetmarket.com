/**
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.zipcloud.spring.social.yahoo.module;

import java.util.Date;
import java.util.List;

/**
 * A contact resource contains information about a user who is known, perhaps through
 * an email exchange, to another user. Contacts are just like entries in an address book.
 * The person who is listed as a contact does not have to give his or her approval.
 * For example, Joe can add everyone from his school to his address book, and he does not
 * need to ask each of them for permission to be added to his list of contacts.
 *
 * @see <a href="https://developer.yahoo.com/social/rest_api_guide/contact-resource.html">Contact resource</a>
 *
 * @author Ruiu Gabriel Mihai (gabriel.ruiu@mail.com)
 */
public class Contact extends YahooObject {

    private int id;
    private Date created;
    private Date updated;
    private String uri;
    private boolean isConnection;
    private List<Field> fields;
    private List<Category> categories;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isConnection() {
        return isConnection;
    }

    public void setConnection(boolean isConnection) {
        this.isConnection = isConnection;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getEmail() {
        return ContactFieldUtils.getValueFromSingleValueField(this, FieldType.EMAIL);
    }

    public String getGuid() {
        return ContactFieldUtils.getValueFromSingleValueField(this, FieldType.GUID);
    }

    public String getYahooId() {
        return ContactFieldUtils.getValueFromSingleValueField(this, FieldType.YAHOOID);
    }

    public String getOtherId() {
        return ContactFieldUtils.getValueFromSingleValueField(this, FieldType.OTHERID);
    }

    public String getCompany() {
        return ContactFieldUtils.getValueFromSingleValueField(this, FieldType.COMPANY);
    }

    public String getNickName() {
        return ContactFieldUtils.getValueFromSingleValueField(this, FieldType.NICKNAME);
    }

    public String getPhone() {
        return ContactFieldUtils.getValueFromSingleValueField(this, FieldType.PHONE);
    }

    public String getJobTitle() {
        return ContactFieldUtils.getValueFromSingleValueField(this, FieldType.JOBTITLE);
    }

    public String getNotes() {
        return ContactFieldUtils.getValueFromSingleValueField(this, FieldType.NOTES);
    }

    public String getLink() {
        return ContactFieldUtils.getValueFromSingleValueField(this, FieldType.LINK);
    }

    public String getCustom() {
        return ContactFieldUtils.getValueFromSingleValueField(this, FieldType.CUSTOM);
    }

    public DateFieldValue getBirthday() {
        return ContactFieldUtils.getDateValue(this, FieldType.BIRTHDAY);
    }

    public DateFieldValue getAnniversary() {
        return ContactFieldUtils.getDateValue(this, FieldType.ANNIVERSARY);
    }

    public NameFieldValue getName() {
        return ContactFieldUtils.getNameValue(this);
    }

    public AddressFieldValue getAddress() {
        return ContactFieldUtils.getAddressValue(this);
    }
}
