package datadog.trace.agent.tooling.bytebuddy.matcher;

import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

/**
 * Additional global matchers that are used to reduce number of classes we try to apply expensive
 * matchers to.
 *
 * <p>This is separated from {@link GlobalIgnoresMatcher} to allow for better testing. The idea is
 * that we should be able to remove this matcher from the agent and all tests should still pass.
 * Moreover, no classes matched by this matcher should be modified during test run.
 */
@HashCodeAndEqualsPlugin.Enhance
public class AdditionalLibraryIgnoresMatcher<T extends TypeDescription>
    extends ElementMatcher.Junction.AbstractBase<T> {

  public static <T extends TypeDescription> Junction<T> additionalLibraryIgnoresMatcher() {
    return new AdditionalLibraryIgnoresMatcher<>();
  }

  /**
   * Be very careful about the types of matchers used in this section as they are called on every
   * class load, so they must be fast. Generally speaking try to only use name matchers as they
   * don't have to load additional info.
   */
  @Override
  public boolean matches(final T target) {
    final String name = target.getActualName();

    if (name.startsWith("org.springframework.cglib.")
        || name.startsWith("org.springframework.aop.")
        || name.startsWith("org.springframework.beans.factory.annotation.")
        || name.startsWith("org.springframework.beans.factory.config.")
        || name.startsWith("org.springframework.beans.factory.parsing.")
        || name.startsWith("org.springframework.beans.factory.xml.")
        || name.startsWith("org.springframework.beans.propertyeditors.")
        || name.startsWith("org.springframework.boot.autoconfigure.cache.")
        || name.startsWith("org.springframework.boot.autoconfigure.condition.")
        || name.startsWith("org.springframework.boot.autoconfigure.http.")
        || name.startsWith("org.springframework.boot.autoconfigure.jackson.")
        || name.startsWith("org.springframework.boot.autoconfigure.web.")
        || name.startsWith("org.springframework.boot.context.")
        || name.startsWith("org.springframework.boot.convert.")
        || name.startsWith("org.springframework.boot.diagnostics.")
        || name.startsWith("org.springframework.boot.web.server.")
        || name.startsWith("org.springframework.boot.web.servlet.")
        || name.startsWith("org.springframework.context.annotation.")
        || name.startsWith("org.springframework.context.event.")
        || name.startsWith("org.springframework.context.expression.")
        || name.startsWith("org.springframework.core.annotation.")
        || name.startsWith("org.springframework.core.convert.")
        || name.startsWith("org.springframework.core.env.")
        || name.startsWith("org.springframework.core.io.")
        || name.startsWith("org.springframework.core.type.")
        || name.startsWith("org.springframework.expression.")
        || name.startsWith("org.springframework.format.")
        || name.startsWith("org.springframework.http.")
        || name.startsWith("org.springframework.ui.")
        || name.startsWith("org.springframework.validation.")
        || name.startsWith("org.springframework.web.context.")
        || name.startsWith("org.springframework.web.filter.")
        || name.startsWith("org.springframework.web.method.")
        || name.startsWith("org.springframework.web.multipart.")
        || name.startsWith("org.springframework.web.util.")) {
      return true;
    }

    return false;
  }
}
