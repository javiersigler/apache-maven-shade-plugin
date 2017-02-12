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
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Collections;

/**
 * Test for {@link SimpleRelocator}.
 *
 * @author Benjamin Bentmann
 *
 */
public class SimpleRelocatorTest
    extends TestCase
{

    public void testCanRelocatePath()
    {
        SimpleRelocator relocator;

        relocator = new SimpleRelocator( "org.foo", null, null, null );
        assertEquals( true, relocator.canRelocatePath( "org/foo/Class" ) );
        assertEquals( true, relocator.canRelocatePath( "org/foo/Class.class" ) );
        assertEquals( true, relocator.canRelocatePath( "org/foo/bar/Class" ) );
        assertEquals( true, relocator.canRelocatePath( "org/foo/bar/Class.class" ) );
        assertEquals( false, relocator.canRelocatePath( "com/foo/bar/Class" ) );
        assertEquals( false, relocator.canRelocatePath( "com/foo/bar/Class.class" ) );
        assertEquals( false, relocator.canRelocatePath( "org/Foo/Class" ) );
        assertEquals( false, relocator.canRelocatePath( "org/Foo/Class.class" ) );

        relocator = new SimpleRelocator( "org.foo", null, null, Arrays.asList(
                "org.foo.Excluded", "org.foo.public.*", "org.foo.Public*Stuff") );
        assertEquals( true, relocator.canRelocatePath( "org/foo/Class" ) );
        assertEquals( true, relocator.canRelocatePath( "org/foo/Class.class" ) );
        assertEquals( true, relocator.canRelocatePath( "org/foo/excluded" ) );
        assertEquals( false, relocator.canRelocatePath( "org/foo/Excluded" ) );
        assertEquals( false, relocator.canRelocatePath( "org/foo/Excluded.class" ) );
        assertEquals( false, relocator.canRelocatePath( "org/foo/public" ) );
        assertEquals( false, relocator.canRelocatePath( "org/foo/public/Class" ) );
        assertEquals( false, relocator.canRelocatePath( "org/foo/public/Class.class" ) );
        assertEquals( true, relocator.canRelocatePath( "org/foo/publicRELOC/Class" ) );
        assertEquals( true, relocator.canRelocatePath( "org/foo/PrivateStuff" ) );
        assertEquals( true, relocator.canRelocatePath( "org/foo/PrivateStuff.class" ) );
        assertEquals( false, relocator.canRelocatePath( "org/foo/PublicStuff" ) );
        assertEquals( false, relocator.canRelocatePath( "org/foo/PublicStuff.class" ) );
        assertEquals( false, relocator.canRelocatePath( "org/foo/PublicUtilStuff" ) );
        assertEquals( false, relocator.canRelocatePath( "org/foo/PublicUtilStuff.class" ) );
    }

    public void testCanRelocateClass()
    {
        SimpleRelocator relocator;

        relocator = new SimpleRelocator( "org.foo", null, null, null );
        assertEquals( true, relocator.canRelocateClass( "org.foo.Class" ) );
        assertEquals( true, relocator.canRelocateClass( "org.foo.bar.Class" ) );
        assertEquals( false, relocator.canRelocateClass( "com.foo.bar.Class" ) );
        assertEquals( false, relocator.canRelocateClass( "org.Foo.Class" ) );

        relocator = new SimpleRelocator( "org.foo", null, null, Arrays.asList(
                "org.foo.Excluded", "org.foo.public.*", "org.foo.Public*Stuff") );
        assertEquals( true, relocator.canRelocateClass( "org.foo.Class" ) );
        assertEquals( true, relocator.canRelocateClass( "org.foo.excluded" ) );
        assertEquals( false, relocator.canRelocateClass( "org.foo.Excluded" ) );
        assertEquals( false, relocator.canRelocateClass( "org.foo.public" ) );
        assertEquals( false, relocator.canRelocateClass( "org.foo.public.Class" ) );
        assertEquals( true, relocator.canRelocateClass( "org.foo.publicRELOC.Class" ) );
        assertEquals( true, relocator.canRelocateClass( "org.foo.PrivateStuff" ) );
        assertEquals( false, relocator.canRelocateClass( "org.foo.PublicStuff" ) );
        assertEquals( false, relocator.canRelocateClass( "org.foo.PublicUtilStuff" ) );
    }

    public void testCanRelocateRawString()
    {
        SimpleRelocator relocator;

        relocator = new SimpleRelocator( "org/foo", null, null, null, true );
        assertEquals( true, relocator.canRelocatePath( "(I)org/foo/bar/Class;" ) );
        
        relocator = new SimpleRelocator( "^META-INF/org.foo.xml$", null, null, null, true );
        assertEquals( true, relocator.canRelocatePath( "META-INF/org.foo.xml" ) );
    }
    
    //MSHADE-119, make sure that the easy part of this works.
    public void testCanRelocateAbsClassPath() 
    {
        SimpleRelocator relocator = new SimpleRelocator( "org.apache.velocity", "org.apache.momentum", null, null );
        assertEquals("/org/apache/momentum/mass.properties", relocator.relocatePath( "/org/apache/velocity/mass.properties" ) );
        
    }

    public void testRelocatePath()
    {
        SimpleRelocator relocator;

        relocator = new SimpleRelocator( "org.foo", null, null, null );
        assertEquals( "hidden/org/foo/bar/Class.class", relocator.relocatePath( "org/foo/bar/Class.class" ) );

        relocator = new SimpleRelocator( "org.foo", "private.stuff", null, null );
        assertEquals( "private/stuff/bar/Class.class", relocator.relocatePath( "org/foo/bar/Class.class" ) );
    }

    public void testRelocateClass()
    {
        SimpleRelocator relocator;

        relocator = new SimpleRelocator( "org.foo", null, null, null );
        assertEquals( "hidden.org.foo.bar.Class", relocator.relocateClass( "org.foo.bar.Class" ) );

        relocator = new SimpleRelocator( "org.foo", "private.stuff", null, null );
        assertEquals( "private.stuff.bar.Class", relocator.relocateClass( "org.foo.bar.Class" ) );
    }

    public void testRelocateRawString()
    {
        SimpleRelocator relocator;

        relocator = new SimpleRelocator( "Lorg/foo", "Lhidden/org/foo", null, null, true );
        assertEquals( "(I)Lhidden/org/foo/bar/Class;", relocator.relocatePath( "(I)Lorg/foo/bar/Class;" ) );

        relocator = new SimpleRelocator( "^META-INF/org.foo.xml$", "META-INF/hidden.org.foo.xml", null, null, true );
        assertEquals( "META-INF/hidden.org.foo.xml", relocator.relocatePath( "META-INF/org.foo.xml" ) );
    }
    
    public void testRelocateMavenFiles()
    {
        SimpleRelocator relocator =
            new SimpleRelocator( "META-INF/maven", "META-INF/shade/maven", null,
                                 Collections.singletonList( "META-INF/maven/com.foo.bar/artifactId/pom.*" ) );
        assertEquals( false, relocator.canRelocatePath( "META-INF/maven/com.foo.bar/artifactId/pom.properties" ) );
        assertEquals( false, relocator.canRelocatePath( "META-INF/maven/com.foo.bar/artifactId/pom.xml" ) );
        assertEquals( true,  relocator.canRelocatePath( "META-INF/maven/com/foo/bar/artifactId/pom.properties" ) );
        assertEquals( true,  relocator.canRelocatePath( "META-INF/maven/com/foo/bar/artifactId/pom.xml" ) );
        assertEquals( true,  relocator.canRelocatePath( "META-INF/maven/com-foo-bar/artifactId/pom.properties" ) );
        assertEquals( true,  relocator.canRelocatePath( "META-INF/maven/com-foo-bar/artifactId/pom.xml" ) );

    }
}
