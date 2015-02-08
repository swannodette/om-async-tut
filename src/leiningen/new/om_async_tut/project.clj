(defproject {{name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :jvm-opts ^:replace ["-Xmx1g" "-server"]

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2727"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.omcljs/om "0.8.7"]
                 [ring "1.3.2"]
                 [compojure "1.3.1"]
                 [figwheel "0.2.2-SNAPSHOT"]
                 [fogus/ring-edn "0.2.0"]
                 [com.datomic/datomic-free "0.9.5130" :exclusions [joda-time]]]

  :plugins [[lein-cljsbuild "1.0.4"]
            [lein-ring "0.9.1"]
            [lein-figwheel "0.2.2-SNAPSHOT"]]


  :source-paths ["src/clj" "src/cljs"]
  :resource-paths ["resources"]
  :clean-targets ^{:protect false} ["resources/public/js/out"
                                    "resources/public/js/main.js"]

  :cljsbuild {
              :builds [{:id "dev"
                        :source-paths ["src/clj" "src/cljs"]
                        :compiler {
                                   :output-to "resources/public/js/main.js"
                                   :output-dir "resources/public/js/out"
                                   :optimizations :none
                                   :source-map true}}]})
