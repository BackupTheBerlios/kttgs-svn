
require "model/Tour"
require "model/StorageInfoLocation"
require "model/General"

require "rexml/document"

class XmlManager

   attr_reader :tour
   
   def initialize(file)
      @file = file
   end
   
   def load
      file = File.new(@file)
      doc = REXML::Document.new(file)
      @tour = Tour.new
      loadStorageInfoLocation(doc)
      loadGeneral(doc)
      @tour
   end

   def loadStorageInfoLocation(doc) 
      sti = doc.root.elements["storage-info/location"]
      display = sti.attributes["display"] ? sti.attributes["display"] : "!NOT GIVEN!"
      location = StorageInfoLocation.new(
         display,
         sti.attributes["country"],
         sti.attributes["region-or-state"],
         sti.attributes["city"],
         sti.attributes["zip-code"]
         )
         
      @tour.storageInfoLocation = location
    end
    
    def loadGeneral(doc)
      desc = doc.root.elements["general/desc"]
      g = General.new(desc)
      @tour.general = g
    end

end
