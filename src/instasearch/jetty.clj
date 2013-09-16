(ns instasearch.jetty
  (:use      instasearch.routes
  	        ring.adapter.jetty)
 (:require [clojure.tools.logging :as logging]
           [clojure.tools.logging.impl :as impl])
  (:import (org.eclipse.jetty.xml XmlConfiguration)
           (org.eclipse.jetty.webapp WebAppContext)) 
  (:gen-class))
(defn init-server [server]
  (try
    (alter-var-root (var logging/*logger-factory*) (constantly (impl/log4j-factory)))
    (let [config (XmlConfiguration. (slurp "src/instasearch/jetty.xml"))]    
      (. config configure server))
    (catch Exception e
      (prn "Unable to load jetty configuration")
      (. e printStackTrace))))


(comment (defn boot []
  (future (run-jetty #'app {:port 7913 :configurator init-server}))))

(defn -main []
  (run-jetty #'app {:port 7913 :configurator init-server :join? false :context "/instasearch"}))
