package com.github.mvysny.vaadingroovybuilder.v14

import com.github.mvysny.vaadingroovybuilder.v14.html.Br
import com.github.mvysny.vaadingroovybuilder.v14.html.Strong
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.Html
import com.vaadin.flow.component.html.*
import com.vaadin.flow.server.AbstractStreamResource
import groovy.transform.CompileStatic
import org.intellij.lang.annotations.Language
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode

import static com.github.mvysny.vaadingroovybuilder.v14.VaadinDsl.init
import static VaadinUtils.text

/**
 * @author mavi
 */
@CompileStatic
class HtmlUtils {
    @NotNull
    static Div div(HasComponents self,
                   @DelegatesTo(value = Div, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Div(), block)
    }

    @NotNull
    static H1 h1(HasComponents self, @NotNull String text = "",
                 @DelegatesTo(value = H1, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new H1(text), block)
    }

    @NotNull
    static H2 h2(HasComponents self, @NotNull String text = "",
                 @DelegatesTo(value = H2, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new H2(text), block)
    }

    @NotNull
    static H3 h3(HasComponents self, @NotNull String text = "",
                 @DelegatesTo(value = H3, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new H3(text), block)
    }

    @NotNull
    static H4 h4(HasComponents self, @NotNull String text = "",
                 @DelegatesTo(value = H4, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new H4(text), block)
    }

    @NotNull
    static H5 h5(HasComponents self, @NotNull String text = "",
                 @DelegatesTo(value = H5, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new H5(text), block)
    }

    @NotNull
    static H6 h6(HasComponents self, @NotNull String text = "",
                 @DelegatesTo(value = H6, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new H6(text), block)
    }

    @NotNull
    static Hr hr(HasComponents self,
                 @DelegatesTo(value = Hr, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Hr(), block)
    }

    @NotNull
    static Paragraph p(HasComponents self, @NotNull String text = "",
                       @DelegatesTo(value = Paragraph, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Paragraph(text), block)
    }

    @NotNull
    static Emphasis em(HasComponents self, @Nullable String text = null,
                       @DelegatesTo(value = Emphasis, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Emphasis(text), block)
    }

    @NotNull
    static Span span(HasComponents self, @Nullable String text = null,
                     @DelegatesTo(value = Span, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Span(text), block)
    }

    @NotNull
    static Anchor anchor(HasComponents self, @NotNull String href = "", @Nullable String text = href,
                         @DelegatesTo(value = Anchor, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Anchor(href, text), block)
    }

    @NotNull
    static Anchor anchor(HasComponents self, @NotNull AbstractStreamResource href, @Nullable String text,
                         @DelegatesTo(value = Anchor, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Anchor(href, text), block)
    }

    @NotNull
    static Image image(HasComponents self, @NotNull String src = "", @NotNull String alt = "",
                       @DelegatesTo(value = Image, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Image(src, alt), block)
    }

    @NotNull
    static Image image(HasComponents self, @NotNull AbstractStreamResource src, @NotNull String alt = "",
                       @DelegatesTo(value = Image, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Image(src, alt), block)
    }

    @NotNull
    static Label label(HasComponents self, @Nullable String text = null, @Nullable Component for_ = null,
                       @DelegatesTo(value = Label, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Label(text).with {
            if (for_ != null) setFor(for_);
            delegate
        }, block)
    }

    @NotNull
    static Input input(HasComponents self,
                       @DelegatesTo(value = Input, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Input(), block)
    }

    @NotNull
    static NativeButton nativeButton(HasComponents self, @Nullable String text = null,
                                     @DelegatesTo(value = NativeButton, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new NativeButton(text), block)
    }

    /**
     * Adds given html snippet into the current element. Way better than {@link Html} since:
     * <ul><li>It doesn't ignore root text nodes</li>
     * <li>It supports multiple elements.</li></ul>
     * Example of use:
     * <pre>
     * div { html("I &lt;strong>strongly&lt;/strong> believe in &lt;i>openness&lt;/i>") }* </pre>
     */
    static void html(HasComponents self, @Language("html") String html) {
        Element doc = Jsoup.parse(html).body()
        for (childNode in doc.childNodes()) {
            if (childNode instanceof TextNode) {
                text(self, (childNode as TextNode).text())
            } else if (childNode instanceof Element) {
                self.add(new Html((childNode as Element).outerHtml()))
            }
        }
    }

    @NotNull
    static Strong strong(HasComponents self,
                         @DelegatesTo(value = Strong, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Strong(), block)
    }

    @NotNull
    static Br br(HasComponents self,
                 @DelegatesTo(value = Br, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Br(), block)
    }

    @NotNull
    static OrderedList ol(HasComponents self, @Nullable OrderedList.NumberingType type = null,
                          @DelegatesTo(value = OrderedList, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, type == null ? new OrderedList() : new OrderedList(type), block)
    }

    @NotNull
    static ListItem li(HasComponents self, @Nullable String text = null,
                       @DelegatesTo(value = ListItem, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, text == null ? new ListItem() : new ListItem(text), block)
    }

    @NotNull
    static IFrame iframe(HasComponents self, @Nullable String src = null,
                         @DelegatesTo(value = IFrame, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, src == null ? new IFrame() : new IFrame(src), block)
    }

    @NotNull
    static Article article(HasComponents self,
                           @DelegatesTo(value = Article, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Article(), block)
    }

    @NotNull
    static Aside aside(HasComponents self,
                       @DelegatesTo(value = Aside, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Aside(), block)
    }

    @NotNull
    static DescriptionList dl(HasComponents self,
                              @DelegatesTo(value = DescriptionList, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new DescriptionList(), block)
    }

    @NotNull
    static DescriptionList.Description dd(HasComponents self,
                                          @DelegatesTo(value = DescriptionList.Description, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new DescriptionList.Description(), block)
    }

    @NotNull
    static DescriptionList.Term dt(HasComponents self,
                                   @DelegatesTo(value = DescriptionList.Term, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new DescriptionList.Term(), block)
    }

    @NotNull
    static Footer footer(HasComponents self,
                         @DelegatesTo(value = Footer, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Footer(), block)
    }

    @NotNull
    static Header header(HasComponents self,
                         @DelegatesTo(value = Header, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Header(), block)
    }

    @NotNull
    static Pre pre(HasComponents self, @Nullable String text = null,
                         @DelegatesTo(value = Pre, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, text == null ? new Pre() : new Pre(text), block)
    }

    @NotNull
    static UnorderedList ul(HasComponents self,
                         @DelegatesTo(value = UnorderedList, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new UnorderedList(), block)
    }
}
