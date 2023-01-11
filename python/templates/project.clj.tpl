(defproject rapanui "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/tools.cli "1.0.206"]
                 [org.clojure/core.match "1.0.0"]
                ;------------------ Database  ------------------ 
                 [org.mybatis/mybatis "3.5.9"]
                 [org.postgresql/postgresql "42.3.3"]
                ;------------------ Logging  ------------------ 
                 [ch.qos.logback/logback-classic "1.2.10"]
                ;------------------ Local libs ------------------ 
                 [rcstadheim/critter-repos "${critter}"]
                 [rcstadheim/nordnet-repos "${nordnet}"]
                 [rcstadheim/oahu "${oahu}"]
                 [rcstadheim/vega "${vega}"]
                ;------------------ Jackson ------------------ 
                 [com.fasterxml.jackson.core/jackson-core "2.12.0"]
                 ;[com.fasterxml.jackson.core/jackson-annotations "2.10.2"]
                 [com.fasterxml.jackson.core/jackson-databind "2.12.0"]
                 [cheshire "5.11.0"]
                ;------------------ Diverse ------------------ 
                 [redis.clients/jedis "3.3.0" :exclusions [org.slf4j/slf4j-api]]
                 [clj-http "3.12.3"]
                 ;[metosin/jsonista "0.2.5"]
                 [org.clojure/tools.namespace "1.1.0"]]
  ;:plugins [[lein-cljfmt "0.7.0"] [lein-virgil "0.1.9"]]
  :plugins [[lein-cljfmt "0.7.0"]]
  :repositories {"project" "file:/home/rcs/opt/java/mavenlocalrepo"}
  :resource-paths ["src/resources"]
  :source-paths ["src/clojure", "/home/rcs/opt/java/tongariki/src/clojure"]
  :test-paths ["test/clojure"]
  :java-source-paths ["src/java"]
  :main ^:skip-aot rapanui.app
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             ;:dev {:ring {:stacktrace-middleware prone.middleware/wrap-exceptions}}
             })
