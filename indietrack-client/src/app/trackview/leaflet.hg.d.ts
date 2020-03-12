import * as Leaflet from 'leaflet'
import * as D3 from 'd3'

declare module 'leaflet' {
  namespace control {
    export function heightgraph():any;
  }
}
