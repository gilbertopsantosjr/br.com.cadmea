/**
 *
 */
package br.com.cadmea.spring.util;

/*
 * Copyright 2002-2016 the original author or authors.
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
 */

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

/**
 * Internal utilities for the conversion package.
 *
 * @author Keith Donald
 * @author Stephane Nicoll
 * @since 3.0
 */
public abstract class ConversionUtils {

    public static Object invokeConverter(final GenericConverter converter, final Object source, final TypeDescriptor sourceType,
                                         final TypeDescriptor targetType) {

        try {
            return converter.convert(source, sourceType, targetType);
        } catch (final ConversionFailedException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new ConversionFailedException(sourceType, targetType, source, ex);
        }
    }

    public static boolean canConvertElements(final TypeDescriptor sourceElementType, final TypeDescriptor targetElementType,
                                             final ConversionService conversionService) {

        if (targetElementType == null) {
            // yes
            return true;
        }
        if (sourceElementType == null) {
            // maybe
            return true;
        }
        if (conversionService.canConvert(sourceElementType, targetElementType)) {
            // yes
            return true;
        } else if (sourceElementType.getType().isAssignableFrom(targetElementType.getType())) {
            // maybe
            return true;
        } else {
            // no
            return false;
        }
    }

    public static Class<?> getEnumType(final Class<?> targetType) {
        Class<?> enumType = targetType;
        while (enumType != null && !enumType.isEnum()) {
            enumType = enumType.getSuperclass();
        }
        if (enumType == null) {
            System.out.println("The target type " + targetType.getName() + " does not refer to an enum");
        }
        return enumType;
    }

}
