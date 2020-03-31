package com.github.mvysny.vaadingroovybuilder.v14.html

import com.github.mvysny.vaadingroovybuilder.v14.VaadinUtils
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

/**
 * @author mavi
 */
@CompileStatic
class HtmlUtils {
    @NotNull
    static Div div(@NotNull HasComponents self,
                   @DelegatesTo(value = Div, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Div(), block)
    }

    @NotNull
    static H1 h1(@NotNull HasComponents self, @NotNull String text = "",
                 @DelegatesTo(value = H1, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new H1(text), block)
    }

    @NotNull
    static H2 h2(@NotNull HasComponents self, @NotNull String text = "",
                 @DelegatesTo(value = H2, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new H2(text), block)
    }

    @NotNull
    static H3 h3(@NotNull HasComponents self, @NotNull String text = "",
                 @DelegatesTo(value = H3, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new H3(text), block)
    }

    @NotNull
    static H4 h4(@NotNull HasComponents self, @NotNull String text = "",
                 @DelegatesTo(value = H4, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new H4(text), block)
    }

    @NotNull
    static H5 h5(@NotNull HasComponents self, @NotNull String text = "",
                 @DelegatesTo(value = H5, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new H5(text), block)
    }

    @NotNull
    static H6 h6(@NotNull HasComponents self, @NotNull String text = "",
                 @DelegatesTo(value = H6, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new H6(text), block)
    }

    @NotNull
    static Hr hr(@NotNull HasComponents self,
                 @DelegatesTo(value = Hr, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Hr(), block)
    }

    @NotNull
    static Paragraph p(@NotNull HasComponents self, @NotNull String text = "",
                       @DelegatesTo(value = Paragraph, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Paragraph(text), block)
    }

    @NotNull
    static Emphasis em(@NotNull HasComponents self, @Nullable String text = null,
                       @DelegatesTo(value = Emphasis, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Emphasis(text), block)
    }

    @NotNull
    static Span span(@NotNull HasComponents self, @Nullable String text = null,
                     @DelegatesTo(value = Span, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Span(text), block)
    }

    @NotNull
    static Anchor anchor(@NotNull HasComponents self, @NotNull String href = "", @Nullable String text = href,
                         @DelegatesTo(value = Anchor, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Anchor(href, text), block)
    }

    @NotNull
    static Anchor anchor(@NotNull HasComponents self, @NotNull AbstractStreamResource href, @Nullable String text,
                         @DelegatesTo(value = Anchor, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Anchor(href, text), block)
    }

    @NotNull
    static Image image(@NotNull HasComponents self, @NotNull String src = "", @NotNull String alt = "",
                       @DelegatesTo(value = Image, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Image(src, alt), block)
    }

    @NotNull
    static Image image(@NotNull HasComponents self, @NotNull AbstractStreamResource src, @NotNull String alt = "",
                       @DelegatesTo(value = Image, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Image(src, alt), block)
    }

    @NotNull
    static Label label(@NotNull HasComponents self, @Nullable String text = null, @Nullable Component for_ = null,
                       @DelegatesTo(value = Label, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Label(text).with {
            if (for_ != null) setFor(for_);
            delegate
        }, block)
    }

    @NotNull
    static Input input(@NotNull HasComponents self,
                       @DelegatesTo(value = Input, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Input(), block)
    }

    @NotNull
    static NativeButton nativeButton(@NotNull HasComponents self, @Nullable String text = null,
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
    static void html(@NotNull HasComponents self, @Language("html") String html) {
        Element doc = Jsoup.parse(html).body()
        for (childNode in doc.childNodes()) {
            if (childNode instanceof TextNode) {
                VaadinUtils.text(self, (childNode as TextNode).text())
            } else if (childNode instanceof Element) {
                self.add(new Html((childNode as Element).outerHtml()))
            }
        }
    }

    @NotNull
    static Strong strong(@NotNull HasComponents self,
                         @DelegatesTo(value = Strong, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Strong(), block)
    }

    @NotNull
    static Br br(@NotNull HasComponents self,
                 @DelegatesTo(value = Br, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Br(), block)
    }

    @NotNull
    static OrderedList ol(@NotNull HasComponents self, @Nullable OrderedList.NumberingType type = null,
                          @DelegatesTo(value = OrderedList, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, type == null ? new OrderedList() : new OrderedList(type), block)
    }

    @NotNull
    static ListItem li(@NotNull HasComponents self, @Nullable String text = null,
                       @DelegatesTo(value = ListItem, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, text == null ? new ListItem() : new ListItem(text), block)
    }

    @NotNull
    static IFrame iframe(@NotNull HasComponents self, @Nullable String src = null,
                         @DelegatesTo(value = IFrame, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, src == null ? new IFrame() : new IFrame(src), block)
    }

    @NotNull
    static Article article(@NotNull HasComponents self,
                           @DelegatesTo(value = Article, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Article(), block)
    }

    @NotNull
    static Aside aside(@NotNull HasComponents self,
                       @DelegatesTo(value = Aside, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Aside(), block)
    }

    @NotNull
    static DescriptionList dl(@NotNull HasComponents self,
                              @DelegatesTo(value = DescriptionList, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new DescriptionList(), block)
    }

    @NotNull
    static DescriptionList.Description dd(@NotNull HasComponents self,
                                          @DelegatesTo(value = DescriptionList.Description, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new DescriptionList.Description(), block)
    }

    @NotNull
    static DescriptionList.Term dt(@NotNull HasComponents self,
                                   @DelegatesTo(value = DescriptionList.Term, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new DescriptionList.Term(), block)
    }

    @NotNull
    static Footer footer(@NotNull HasComponents self,
                         @DelegatesTo(value = Footer, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Footer(), block)
    }

    @NotNull
    static Header header(@NotNull HasComponents self,
                         @DelegatesTo(value = Header, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Header(), block)
    }

    @NotNull
    static Pre pre(@NotNull HasComponents self, @Nullable String text = null,
                         @DelegatesTo(value = Pre, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, text == null ? new Pre() : new Pre(text), block)
    }

    @NotNull
    static UnorderedList ul(@NotNull HasComponents self,
                         @DelegatesTo(value = UnorderedList, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new UnorderedList(), block)
    }
}
