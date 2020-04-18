import * as L from 'leaflet'
import * as geojson from 'geojson';
import * as llss from 'leaflet-linestring-select'

declare module 'leaflet' {

  namespace Control {

    class LineStringSelect extends Control {
      constructor( options: LineStringSelectOptions);

      enable(options: LineStringEnableOptions);

      disable();

      reset();

      selectMeters(startMeter, endMeter);

      toGeoJSON();

      getSelection(): L.LatLng[];
    }
  }

  export interface LineStringSelectOptions {
    startMarkerClass: string
  }

  export interface LineStringEnableOptions {
    layer: L.Polyline;
    feature?: geojson.Feature;
  }
}
