(defproject instasearch "0.0.3"
  :description "Searching Instagram API"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [ring/ring-core "1.2.0" :exclusions [javax.servlet/servlet-api]]
                 [ring/ring-servlet "1.2.0" :exclusions [javax.servlet/servlet-api]]
                 [clj-json "0.5.0"]
                 [http-kit "2.1.10"]
                 [cheshire "5.2.0"]

                 [ring/ring-jetty-adapter "1.1.5" :exclusions [org.mortbay.jetty/servlet-api]]
                 [lein-ring "0.7.5"]
                 [ring/ring-devel "1.1.0"]
                 [org.mortbay.jetty/jetty-plus "6.1.25"]
                 [org.mortbay.jetty/jetty-naming "6.1.25"]

                 [ring/ring-jetty-adapter "1.1.5"]
                 [org.eclipse.jetty/jetty-xml "7.6.1.v20120215"]
                 [org.eclipse.jetty/jetty-webapp "7.6.1.v20120215"]
                 [org.eclipse.jetty/jetty-plus "7.6.1.v20120215"]                 
                 [javax.servlet/servlet-api "2.5"]   
                ]

  :plugins [[lein-ring "0.7.5"]]

  :repositories {"yousee-releases" {:url "http://yousee.artifactoryonline.com/yousee/libs-releases"
                                    :username "deployer"
                                    :password :env}}

   :dev-dependencies [
  ;                    [ring/ring-jetty-adapter "1.0.0" :exclusions [org.mortbay.jetty/servlet-api]]
  ;                    [lein-ring "0.7.5"]
  ;                    [ring/ring-devel "1.1.0"]
  ;                    [org.mortbay.jetty/jetty-plus "6.1.25"]
  ;                    [org.mortbay.jetty/jetty-naming "6.1.25"]
                     
                     ]

  :profiles {:dev {:dependencies [ [clj-stacktrace "0.2.4"]

                                   ; [ring/ring-jetty-adapter "1.1.5"]
                                   ; [org.eclipse.jetty/jetty-xml "7.6.1.v20120215"]
                                   ; [org.eclipse.jetty/jetty-webapp "7.6.1.v20120215"]
                                   ; [org.eclipse.jetty/jetty-plus "7.6.1.v20120215"]

;  lidt for ny?                     [javax.servlet/javax.servlet-api "3.0.1"]
;                                   [javax.servlet/servlet-api "2.5"]
                                   [acceptit "1.0.5" :exclusions [org.clojure/clojure]] ;; for testing rest
                                 ;[swank-clojure "1.3.3"]
                                 ]}

  ;:jvm-opts ["-Dhttp.proxyHost=localhost" "-Dhttp.proxyPort=3128"]                    

                                 }


  ;:war {:web-content "resources"} ;"resources" is default.
  ;:aot [messaging-api.servlet]
  ;:cucumber-feature-paths ["test/features/"]
  :jvm-opts ["-Dfile.encoding=UTF-8" "-Dcatalina.base=./"]

  :bootclaspath true
  :warn-on-reflection false
  :ring {:handler instasearch.routes/app
         :servlet-path-info?  false ; enabler :context i routes.
;         :web-xml "web.xml"
       }
  :main instasearch.jetty

 )

