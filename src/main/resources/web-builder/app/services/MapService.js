import {Injector} from 'ngES6';
import angular from 'angular';
import $ from 'jquery';
import './map-style.css';

export default class MapService extends Injector {
    static $inject = [];

    BackButtonCtrl = class extends window.BMap.Control {
        constructor(anchor, offsetX, offsetY) {
            super();
            this.defaultAnchor = anchor;
            this.defaultOffset = new window.BMap.Size(offsetX, offsetY);
        }

        initialize(map) {
            this.map = map;
            let div  = $('<div class="map-ctrl">返回</div>').on('click', () => {
                angular.element(div).scope().$emit('map.action.back', map);
            });
            map.getContainer().appendChild(div[0]);
            return div[0];
        }
    };

    FullScreenCtrl = class extends window.BMap.Control {
        constructor(anchor, offsetX, offsetY) {
            super();
            this.defaultAnchor = anchor;
            this.defaultOffset = new window.BMap.Size(offsetX, offsetY);
        }

        initialize(map) {
            this.map = map;
            let div = $(`<div class="map-ctrl"><i class="glyphicon glyphicon-fullscreen"></i></div>`).on('click', () => {
                $(map.getContainer()).toggleClass('map-fullscreen');
            });
            map.getContainer().appendChild(div[0]);
            return div[0];
        }
    };

    HtmlMarkerOverlay = class extends window.BMap.Overlay {
        constructor(scope, point, template, offset) {
            super();
            this.scope    = scope;
            this.point    = point;
            this.template = template;
            this.offset   = offset || {};
        }

        initialize(map) {
            const $injector    = angular.injector(['ng']);
            const $compile     = $injector.get('$compile');
            const $interpolate = $injector.get('$interpolate');
            this.map           = map;
            let template       = this.template || `<div class="mv-marker">
                                                            <span>★</span>
                                                            <div class="arrow"></div>
                                                        </div>`;
            let div            = this.div = $($compile($interpolate(template)(this.scope))(this.scope));
            div.click(e => {
                this.scope.$emit('map.marker.click', e);
            }).mouseenter(e => {
                this.scope.$emit('map.marker.mouseenter', e);
            }).mouseleave(e => {
                this.scope.$emit('map.marker.mouseleave', e);
            });
            map.getPanes().markerPane.appendChild(div[0]);
            return div[0];
        }

        draw() {
            let pixel = this.map.pointToOverlayPixel(this.point);

            if (this.offset.x === undefined) {
                this.offset.x = -10;
            } else if (typeof this.offset.x === 'function') {
                this.offset.x = this.offset.x(this.div.width());
            }
            if (this.offset.y === undefined) {
                this.offset.y = -(this.div.height() + 10);
            } else if (typeof this.offset.y === 'function') {
                this.offset.y = this.offset.y(this.div.height());
            }

            this.div.css({
                left: pixel.x + this.offset.x,
                top : pixel.y + this.offset.y,
            });
        }

        getPosition() {
            return this.point;
        }

        getMap() {
            return this.map;
        }
    };
}
