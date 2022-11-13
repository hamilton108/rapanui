(defproject rapanui "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/tools.cli "1.0.206"]
                 [org.mybatis/mybatis "3.5.9"]
                 [org.postgresql/postgresql "42.3.3"]
                 [org.jsoup/jsoup "1.11.3"]
                 [org.clojure/core.match "1.0.0"]
                ;------------------ Local libs ------------------ 
                 [rcstadheim/critter-repos "3.0.0-20221111.131058-4"]
                 [rcstadheim/nordnet-repos "3.0.0-20221111.131149-4"]
                 [rcstadheim/oahu "3.0.0-20221111.131117-4"]
                 [rcstadheim/vega "3.0.0-20221111.131134-4"]
                ;------------------ Diverse ------------------ 
                 [net.sourceforge.htmlunit/htmlunit "2.44.0"
                  :exclusions [org.eclipse.jetty/jetty-http org.eclipse.jetty/jetty-io]]
                 [prone "2020-01-17"]
                 [redis.clients/jedis "3.3.0" :exclusions [org.slf4j/slf4j-api]]
                 ;[cider/cider-nrepl "0.25.3" :exclusions [nrepl]]
                 ;[metosin/jsonista "0.2.5"]
                 [org.clojure/tools.namespace "1.1.0"]]
  ;:plugins [[lein-cljfmt "0.7.0"] [lein-virgil "0.1.9"]]
  :plugins [[lein-cljfmt "0.7.0"]]
  :repositories {"project" "file:/home/rcs/opt/java/mavenlocalrepo"}
  :resource-paths ["src/resources"]
  :source-paths ["src/clojure"]
  :test-paths ["test/clojure"]
  :java-source-paths ["src/java"]
  :main ^:skip-aot rapanui.app
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             ;:dev {:ring {:stacktrace-middleware prone.middleware/wrap-exceptions}}
             })
