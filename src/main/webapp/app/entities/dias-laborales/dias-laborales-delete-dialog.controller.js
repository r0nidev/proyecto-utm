(function() {
    'use strict';

    angular
        .module('utmApp')
        .controller('DiasLaboralesDeleteController',DiasLaboralesDeleteController);

    DiasLaboralesDeleteController.$inject = ['$uibModalInstance', 'entity', 'DiasLaborales'];

    function DiasLaboralesDeleteController($uibModalInstance, entity, DiasLaborales) {
        var vm = this;

        vm.diasLaborales = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DiasLaborales.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
