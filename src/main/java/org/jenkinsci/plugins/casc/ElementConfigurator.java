/*
 * Copyright (c) 2018 CloudBees, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to
 * whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.jenkinsci.plugins.casc;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Set;

/**
 * Define a {@link Configurator} which handles a configuration element, identified by name.
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 * @author Oleg Nenashev
 * @see RootElementConfigurator
 */
public interface ElementConfigurator<T> {

    /**
     * Get a configurator name.
     * @return short name for this component when used in a configuration.yaml file
     */
    @Nonnull
    String getName();

    /**
     * Determine the list of Attribute available for configuration of the managed component.
     *
     * @return A set of {@link Attribute}s that describes this object
     */
    @Nonnull
    Set<Attribute> describe();

    /**
     * Configures/creates a Jenkins object based on a tree.
     *
     * @param config
     *      Map/List/primitive objects (think YAML) that represents the configuration from which
     *      a Jenkins object is configured.
     * @return
     *      Fully configured Jenkins object that results from this configuration.
     *      {@code null} if no new objects got created, but in such case some existing objects may have been modified
     * @throws ConfiguratorException if something went wrong, depends on the concrete implementation
     */
    @CheckForNull
    T configure(Object config) throws ConfiguratorException;

    /**
     * Configures/creates a Jenkins object based on a tree.
     *
     * @param config
     *      Map/List/primitive objects (think YAML) that represents the configuration from which
     *      a Jenkins object is configured.
     * @return
     *      Fully configured Jenkins object that results from this configuration.
     * @throws ConfiguratorException if something went wrong, depends on the concrete implementation.
     *      Also throws exception if the converted object is {@code null}
     */
    @Nonnull
    default T configureNonNull(Object config) throws ConfiguratorException {
        T converted = configure(config);
        if (converted == null) {
            throw new ConfiguratorException(this, "Converted object is null, but a non-null object is required");
        }
        return converted;
    }
}
