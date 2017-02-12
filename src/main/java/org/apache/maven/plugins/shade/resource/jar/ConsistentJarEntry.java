package org.apache.maven.plugins.shade.resource.jar;

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

import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.jar.JarEntry;

/**
 * Wrapper over JarEntry to set fix the zip entries times
 */
public class ConsistentJarEntry extends JarEntry
{

    private static final FileTime FIRST_EPOCH = FileTime.from( Instant.ofEpochMilli( 1 ) );

    public ConsistentJarEntry( String name, boolean consistentDates )
    {
        super( name );
        if ( consistentDates )
        {
            this.setTime( 1 );
            this.setCreationTime( FIRST_EPOCH );
            this.setLastAccessTime( FIRST_EPOCH );
            this.setLastModifiedTime( FIRST_EPOCH );
        }
    }

    public ConsistentJarEntry( String name, long lastModified, boolean consistentDates )
    {
        this( name, consistentDates );
        if ( !consistentDates )
        {
            FileTime fileTime = FileTime.from( Instant.ofEpochMilli( lastModified ) );
            this.setLastModifiedTime( fileTime );
        }
    }
}
