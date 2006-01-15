#require 'abstract_application'
#require 'intro_helper'

require 'xml/XmlManager'
require "model/StorageInfoLocation"
require "model/Tour"

class IntroController < ApplicationController
  layout "intro"
  include IntroHelper
    
  def index
     #render_text "Test"
     #render_file "application"
     #@session['number'] = 0 unless @session['number']
     #@session['number'] = @session['number'] + 1
  end
  
  def navigation
     "[no navigation]"
  end
  
  def stored_tours_list
     ret = []
     Dir.foreach("db/xml") {
        |dir|
        if dir != "." && dir != ".."
           f = "db/xml/" + dir +"/" + dir +".xml" 
           mgr = XmlManager.new(f)
           tour = mgr.load
           # display = tour.storageInfoLocation.display
           tour.tid = dir
           ret.push tour
        end
     }
     ret
  end
  
  def get_number
     @session['number']
  end
  
  def create
     error = true
     if error
        #flash['create_error'] = 'Create error (' + @params['dir']['dir'] + ')'
        flash['create_error'] = 'This error is by intention. I just have not yet implemented this feature'
        redirect_to :action => 'index'
     else
        @session['dir'] = @params['dir']['dir']
        redirect_to :controller => 'create', :action => 'step1'
        #redirect_to :action => 'index'
     end
  end
  
  def description
     tid = @params['tid']
     f = "db/xml/" + tid +"/" + tid +".xml" 
     mgr = XmlManager.new(f)
     tour = mgr.load
     @session['tour'] = tour
     @session['tid'] = tid
  end
  
  def edit
     error = true
     if error
        flash['edit_error'] = 'Edit error: Not yet implemented'
        redirect_to :action => 'index'
     else
        @session['dir'] = @params['dir']['dir']
        redirect_to :controller => 'create', :action => 'step1'
     end
  end
  
  def download
     error = true
     if error
        flash['download_error'] = 'Download: Not yet implemented'
        redirect_to :action => 'index'
     else
        @session['dir'] = @params['dir']['dir']
        redirect_to :controller => 'download', :action => 'index'
     end
  end


end
