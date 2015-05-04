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
package edu.zipcloud.spring.social.yahoo.api;


import java.util.List;

import edu.zipcloud.spring.social.yahoo.filter.ContactsFilter;
import edu.zipcloud.spring.social.yahoo.module.Category;
import edu.zipcloud.spring.social.yahoo.module.Contact;
import edu.zipcloud.spring.social.yahoo.module.Contacts;

/**
 * Main interface for interacting with the Contacts-part of the Yahoo Social API.
 *
 * @see <a href="https://developer.yahoo.com/social/rest_api_guide/contact_api.html">Contacts API</a>
 *
 * @author Ruiu Gabriel Mihai (gabriel.ruiu@mail.com)
 */
public interface ContactsOperations {

    /**
     * Retrieves every contact which belongs to the currently logged in user
     */
    Contacts getContacts();

    /**
     * Retrieves every contact which belongs to the currently logged in user and respects the filter constraints
     * @see {@link com.github.gabrielruiu.springsocial.yahoo.filter.ContactsFilter}
     */
    Contacts getContacts(ContactsFilter filter);

    /**
     * Retrieves to contacts which belong to the given category
     */
    Contacts getContactsByCategory(String categoryName);

    /**
     * Retrieves the contract with the given contact id
     */
    Contact getContact(int contactCid);

    /**
     * Retrieves all the categories which belong to the currently logged in user
     */
    List<Category> getCategories();

    /**
     * Retrieves the categories to which the contact (given by the contact id) belongs
     */
    List<Category> getCategoriesByContactCid(int contactCid);
}