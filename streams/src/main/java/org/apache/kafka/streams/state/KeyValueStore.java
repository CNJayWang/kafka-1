/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.kafka.streams.state;

import org.apache.kafka.common.annotation.InterfaceStability;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.processor.StateStore;

import java.util.List;

/**
 * A key-value store that supports put/get/delete and range queries.
 *
 * @param <K> The key type
 * @param <V> The value type
 */
@InterfaceStability.Unstable
public interface KeyValueStore<K, V> extends StateStore {

    /**
     * Get the value corresponding to this key
     *
     * @param key The key to fetch
     * @return The value or null if no value is found.
     * @throws NullPointerException If null is used for key.
     */
    V get(K key);

    /**
     * Update the value associated with this key
     *
     * @param key They key to associate the value to
     * @param value The value
     * @throws NullPointerException If null is used for key or value.
     */
    void put(K key, V value);

    /**
     * Update the value associated with this key, unless a value
     * is already associated with the key
     *
     * @param key They key to associate the value to
     * @param value The value
     * @return The old value or null if there is no such key.
     * @throws NullPointerException If null is used for key or value.
     */
    V putIfAbsent(K key, V value);

    /**
     * Update all the given key/value pairs
     *
     * @param entries A list of entries to put into the store.
     * @throws NullPointerException If null is used for any key or value.
     */
    void putAll(List<KeyValue<K, V>> entries);

    /**
     * Delete the value from the store (if there is one)
     *
     * @param key The key
     * @return The old value or null if there is no such key.
     * @throws NullPointerException If null is used for key.
     */
    V delete(K key);

    /**
     * Get an iterator over a given range of keys. This iterator MUST be closed after use.
     *
     * @param from The first key that could be in the range
     * @param to The last key that could be in the range
     * @return The iterator for this range.
     * @throws NullPointerException If null is used for from or to.
     */
    KeyValueIterator<K, V> range(K from, K to);

    /**
     * Return an iterator over all keys in the database. This iterator MUST be closed after use.
     *
     * @return An iterator of all key/value pairs in the store.
     */
    KeyValueIterator<K, V> all();

    /**
     * Return an approximate count of key-value mappings in this store.
     *
     * The count is not guaranteed to be exact in order to accommodate stores
     * where an exact count is expensive to calculate.
     *
     * @return an approximate count of key-value mappings in the store.
     */
    long approximateNumEntries();

}
