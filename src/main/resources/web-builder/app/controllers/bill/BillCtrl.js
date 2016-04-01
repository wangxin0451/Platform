import {Injector} from 'ngES6';

export default class BillCtrl extends Injector {
    static $inject = ['$scope', 'BillService'];
    static $template = require('./bill.html');

    constructor(...args) {
        super(...args);
        const {$scope} = this.$injected;
        $scope.filter     = {};
        $scope.pagination = {page: 1, totalItems: 0, pageSize: 15};
        this.attachMethodsTo($scope);
        this.getBills();
    }

    getBills() {
        const {$scope, BillService} = this.$injected;
        const {page, pageSize} = $scope.pagination;
        BillService.search($scope.filter, page, pageSize).then(res => {
            if (res.data && res.data.result) {
                $scope.bills                 = res.data.data.list;
                $scope.pagination.totalItems = res.data.data.totalElements;
            }
        });
    }

    payoff(bill) {
        const {BillService} = this.$injected;
        BillService.payoff(bill.id).then(res => {
            if (res.data && res.data.result) {
                bill.paid = true;
            }
        });
    }

    reset() {
        const {$scope} = this.$injected;
        $scope.filter     = {};
        $scope.pagination = {...$scope.pagination, page: 1, totalItems: 0};
    }

    async generate() {
        const {BillService} = this.$injected;
        await BillService.generate();
        this.getBills();
    }

    async clear() {
        const {BillService} = this.$injected;
        await BillService.clear();
        this.getBills();
    }
}
