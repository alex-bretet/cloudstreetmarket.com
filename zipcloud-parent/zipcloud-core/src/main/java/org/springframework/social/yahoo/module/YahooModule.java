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
package org.springframework.social.yahoo.module;

import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Jackson module that maps mixins with Yahoo objects
 *
 * @author Ruiu Gabriel Mihai (gabriel.ruiu@mail.com)
 */
public class YahooModule extends SimpleModule {

    /**
	 * 
	 */
	private static final long serialVersionUID = 602565664393306354L;

	public YahooModule() {
        super("YahooModule");
    }

    @Override
    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(ContactsWrapper.class, ContactsWrapperMixin.class);
        context.setMixInAnnotations(Contacts.class, ContactsMixin.class);
        context.setMixInAnnotations(Contact.class, ContactMixin.class);
        context.setMixInAnnotations(ContactWrapper.class, ContactWrapperMixin.class);
        context.setMixInAnnotations(Category.class, CategoryMixin.class);
        context.setMixInAnnotations(Categories.class, CategoriesMixin.class);
        context.setMixInAnnotations(CategoriesWrapper.class, CategoriesWrapperMixin.class);
        context.setMixInAnnotations(Field.class, FieldMixin.class);
        context.setMixInAnnotations(AddressFieldValue.class, AddressFieldValueMixin.class);
        context.setMixInAnnotations(DateFieldValue.class, DateFieldValueMixin.class);
        context.setMixInAnnotations(NameFieldValue.class, NameFieldValueMixin.class);
        context.setMixInAnnotations(SingleFieldValue.class, SingleFieldValueMixin.class);
    }
}
