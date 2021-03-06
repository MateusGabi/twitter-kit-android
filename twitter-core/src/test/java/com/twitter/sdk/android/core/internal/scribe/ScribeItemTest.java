/*
 * Copyright (C) 2015 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.twitter.sdk.android.core.internal.scribe;

import com.twitter.sdk.android.core.BuildConfig;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.TweetBuilder;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.models.UserBuilder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ScribeItemTest {
    static final long TEST_ID = 123;
    static final String TEST_MESSAGE = "test message";

    @Test
    public void testFromTweet() {
        final Tweet tweet = new TweetBuilder().setId(TEST_ID).build();
        final ScribeItem item = ScribeItem.fromTweet(tweet);

        assertEquals(Long.valueOf(TEST_ID), item.id);
        assertEquals(Integer.valueOf(ScribeItem.TYPE_TWEET), item.itemType);
        assertNull(item.description);
    }

    @Test
    public void testFromUser() {
        final User user = new UserBuilder().setId(TEST_ID).build();
        final ScribeItem item = ScribeItem.fromUser(user);

        assertEquals(Long.valueOf(TEST_ID), item.id);
        assertEquals(Integer.valueOf(ScribeItem.TYPE_USER), item.itemType);
        assertNull(item.description);
    }

    @Test
    public void testFromMessage() {
        final ScribeItem item = ScribeItem.fromMessage(TEST_MESSAGE);

        assertNull(item.id);
        assertEquals(Integer.valueOf(ScribeItem.TYPE_MESSAGE), item.itemType);
        assertEquals(TEST_MESSAGE, item.description);
    }

    @Test
    public void testBuilder() {
        final ScribeItem item = new ScribeItem.Builder()
                .setId(TEST_ID)
                .setItemType(ScribeItem.TYPE_MESSAGE)
                .setDescription(TEST_MESSAGE)
                .build();

        assertEquals(Long.valueOf(TEST_ID), item.id);
        assertEquals(Integer.valueOf(ScribeItem.TYPE_MESSAGE), item.itemType);
        assertEquals(TEST_MESSAGE, item.description);
    }

    @Test
    public void testBuilder_empty() {
        final ScribeItem item = new ScribeItem.Builder().build();

        assertNull(item.id);
        assertNull(item.itemType);
        assertNull(item.description);
    }
}
