
class StorageInfoLocation

   attr_reader :display, :country, :state, :city, :zip
   
   def initialize(display, country, state, city , zip)
      @display = display 
      @country = country
      @state   = state
      @city    = city
      @zip     = zip
   end
   
   def to_s
      @display + ", " + @country + ", " + @state + ", " + @city + ", " + @zip
      #"@country, @state, @city, @zip"
   end

end
