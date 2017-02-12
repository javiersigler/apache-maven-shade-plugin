package org.apache.maven.plugins.shade.relocation;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import junit.framework.TestCase;

import java.util.Collections;

public class SimpleRelocatorParameterTest
    extends TestCase
{


    protected void setUp()
        throws Exception
    {
        super.setUp();
    }

    public void testThatNullPatternInConstructorShouldNotThrowNullPointerException()
    {
        constructThenFailOnNullPointerException( null, "" );
    }

    public void testThatNullShadedPatternInConstructorShouldNotThrowNullPointerException()
    {
        constructThenFailOnNullPointerException( "", null );
    }

    private void constructThenFailOnNullPointerException( String pattern, String shadedPattern )
    {
        try
        {
            new SimpleRelocator( pattern, shadedPattern, Collections.<String> emptyList(), Collections.<String> emptyList() );
        }
        catch ( NullPointerException e )
        {
            fail( "Constructor should not throw null pointer exceptions" );
        }
    }
}
