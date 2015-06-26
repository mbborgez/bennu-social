package org.fenixedu.bennu.social.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresDropbox {

    /*
     * Scopes should be used in a way the user can configure correctly the application on Dropbox interface.
     * Bad configuration may lead to unexpected errors
     * See https://www.dropbox.com/developers/reference/devguide#app-permissions (link may be outdated)
     */
    DropboxScopes[] scopes() default {};

    public enum DropboxScopes {

        OWN_FOLDER,

        ALL_FOLDERS,

        ALL_FILE_TYPES,

        SPECIFIC_FILE_TYPES,

        TEXT_FILE,

        DOC_FILE,

        IMAGE_FILE,

        VIDEO_FILE,

        AUDIO_FILE,

        EBOOKS;

        public String getQualifiedName() {
            return DropboxScopes.class.getSimpleName() + "." + name();
        }
    }
}
