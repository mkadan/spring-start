package de.apollon.newmedia.newsquare.live.importer.aaz.util.annotation;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierDefault;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Nonnull
@TypeQualifierDefault({
        ElementType.FIELD,
        ElementType.PARAMETER,
        ElementType.METHOD,
        ElementType.LOCAL_VARIABLE,
})
@Retention(RetentionPolicy.CLASS)
public @interface NonnullByDefault {
}
