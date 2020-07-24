(function() {
    'use strict';

    angular
        .module('utmApp')
        .controller('DiasLaboralesDialogController', DiasLaboralesDialogController);

    DiasLaboralesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DiasLaborales'];

    function DiasLaboralesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DiasLaborales) {
        var vm = this;

        vm.diasLaborales = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.diasLaborales.id !== null) {
                DiasLaborales.update(vm.diasLaborales, onSaveSuccess, onSaveError);
            } else {
                DiasLaborales.save(vm.diasLaborales, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('utmApp:diasLaboralesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
