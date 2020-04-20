import * as geojson from 'geojson'

export interface SegmentGeoJson {
  segmentId: number;
  geoJson: geojson.GeoJsonObject;
}
