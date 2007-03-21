#require 'webgen/plugins/tags/tags'

#module Tags
   class GlobalVars < Tags::DefaultTag
   
   infos( :name => 'Custom/FileMDate', :summary => "Puts the modification time of the file on the page" )

   register_tag 'globalvar'

   #add_param 'varsHash', {}, 'hash with global vars, use config.yaml to set vars'
   #add_param 'var', nil, 'the variable to output'
   #set_mandatory 'var', true
   
   #def process_tag( tag, node, refNode )
   def process_tag( tag, chain)
     #get_param( 'varsHash' )[get_param('var')].to_s
   end
   
   end
#end

