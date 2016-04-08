import {Injector} from 'ngES6';
import angular from 'angular';
import $ from 'jquery';

export default class TechnicianMapCtrl extends Injector {
    static $inject = ['$scope', 'Settings', 'TechnicianService', '$compile', '$timeout', '$sce'];
    static $template = require('./map.html');

    constructor(...args) {
        super(...args);
        const {$scope} = this.$injected;
        this.attachMethodsTo($scope);
        $scope.items = [
            {lng: 114.211221, lat: 30.650637, label: '武汉1'},
            {lng: 114.292284, lat: 30.645665, label: '武汉2'},
            {lng: 114.326491, lat: 30.636717, label: '武汉3'},
            {lng: 114.350638, lat: 30.608375, label: '武汉4'},
            {lng: 114.350925, lat: 30.601909, label: '武汉5'},
            {lng: 114.340433, lat: 30.592335, label: '武汉6'},
            {lng: 116.381359, lat: 39.940228, label: '北京1'},
            {lng: 116.438850, lat: 39.959698, label: '北京2'},
            {lng: 116.459547, lat: 39.921196, label: '北京3'},
            {lng: 121.464139, lat: 31.285694, label: '上海1'},
            {lng: 121.554976, lat: 31.251124, label: '上海2'},
            {lng: 121.457240, lat: 31.203694, label: '上海3'},
        ];
    }

    onItemClick(scope, evt) {
        const {$compile, $timeout} = this.$injected;
        let element = $(evt.target);
        if (!element.hasClass('mv-marker')) {
            $timeout(() => {
                element.closest('.mv-marker').click();
            });
            return;
        }
        if (element.data('has-popover')) return;

        element.attr('uib-popover', scope.data.label);
        element.attr('popover-append-to-body', true);
        element.attr('popover-trigger', 'outsideClick');
        element.attr('popover-placement', 'right');
        element.attr('popover-is-open', true);
        $compile(element)(angular.element(element).scope());
        element.data('has-popover', true);
    }
}
