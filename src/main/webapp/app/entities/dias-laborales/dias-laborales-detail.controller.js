(function() {
    'use strict';

    angular
        .module('utmApp')
        .controller('DiasLaboralesDetailController', DiasLaboralesDetailController);

    DiasLaboralesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DiasLaborales'];

    function DiasLaboralesDetailController($scope, $rootScope, $stateParams, previousState, entity, DiasLaborales) {
        var vm = this;

        vm.diasLaborales = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('utmApp:diasLaboralesUpdate', function(event, result) {
            vm.diasLaborales = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
